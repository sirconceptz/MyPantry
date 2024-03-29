package com.hermanowicz.pantry.navigation.features.productDetails.ui

import android.graphics.Bitmap
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.photo.FetchPhotoBitmapUseCase
import com.hermanowicz.pantry.domain.photo.SetPhotoFileUseCase
import com.hermanowicz.pantry.domain.product.CheckIsProductsHashcodeCorrectUseCase
import com.hermanowicz.pantry.domain.product.DeleteNotificationForProductsUseCase
import com.hermanowicz.pantry.domain.product.DeleteProductsUseCase
import com.hermanowicz.pantry.domain.product.GetGroupProductByIdUseCase
import com.hermanowicz.pantry.domain.product.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.product.ParseDeprecatedDatabaseProductsUseCase
import com.hermanowicz.pantry.domain.product.UpdateProductsUseCase
import com.hermanowicz.pantry.domain.scanner.BuildScanOptionsUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsState
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsUiState
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.ProductDetailsOption
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val observeAllProductsUseCase: ObserveAllProductsUseCase,
    private val getGroupProductByIdUseCase: GetGroupProductByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase,
    private val checkIsProductsHashcodeCorrectUseCase: CheckIsProductsHashcodeCorrectUseCase,
    private val parseDeprecatedDatabaseProductsUseCase: ParseDeprecatedDatabaseProductsUseCase,
    private val observeAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase,
    private val updateProductsUseCase: UpdateProductsUseCase,
    private val setPhotoFileUseCase: SetPhotoFileUseCase,
    private val fetchPhotoBitmapUseCase: FetchPhotoBitmapUseCase,
    private val deleteProductsUseCase: DeleteProductsUseCase,
    private val deleteNotificationForProductsUseCase: DeleteNotificationForProductsUseCase,
    private val buildScanOptionsUseCase: BuildScanOptionsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailsState())
    val state: StateFlow<ProductDetailsState> = _state

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Loading)
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    private val idAndHashcode: String = savedStateHandle["idAndHashcode"] ?: ";"
    private val argumentsArray = idAndHashcode.split(";")
    val productId = argumentsArray[0].toInt()
    private val productHashcode = argumentsArray[1]
    var groupProduct: GroupProduct = GroupProduct()

    init {
        fetchProducts(productId)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    fun fetchProducts(productId: Int) {
        lateinit var databaseMode: DatabaseMode
        lateinit var ownCategories: List<Category>
        viewModelScope.launch(Dispatchers.IO) {
            try {
                observeDatabaseModeUseCase().flatMapLatest {
                    databaseMode = it
                    observeAllOwnCategoriesUseCase(databaseMode)
                }
                    .flatMapLatest {
                        ownCategories = it
                        observeAllProductsUseCase(databaseMode)
                    }.collect { products ->
                        updateProductUiState(products, ownCategories, productId, databaseMode)
                    }
            } catch (e: Exception) {
                _uiState.value = ProductDetailsUiState.Error(e.toString())
                Timber.e(e.message)
            }
        }
    }

    private fun updateProductUiState(
        products: List<Product>,
        ownCategories: List<Category>,
        productId: Int,
        databaseMode: DatabaseMode
    ) {
        val parsedProducts =
            parseDeprecatedDatabaseProductsUseCase(products, ownCategories)
        if (checkIsProductsHashcodeCorrectUseCase(
                productId,
                productHashcode,
                products
            )
        ) {
            setProductsInState(productId, parsedProducts)
            setPhotoPreviewIfPhotoExist(products, databaseMode)
        } else {
            if (!state.value.onNavigateToMyPantry) {
                _uiState.value =
                    ProductDetailsUiState.Error("No correct product")
            } else {
                _uiState.value = ProductDetailsUiState.Loading
            }
        }
    }

    private fun setProductsInState(
        productId: Int,
        parsedProducts: List<Product>
    ) {
        groupProduct = getGroupProductByIdUseCase(
            productId,
            parsedProducts
        )
        _uiState.value = ProductDetailsUiState.Loaded(
            ProductDetailsModel(
                groupProduct = groupProduct,
                loadingVisible = false
            )
        )
    }

    private fun setPhotoPreviewIfPhotoExist(
        products: List<Product>,
        databaseMode: DatabaseMode
    ) {
        if (products.isNotEmpty()) {
            val fileName = products[0].photoName
            setPhotoFileUseCase(fileName)
            val photoBitmap =
                fetchPhotoBitmapUseCase(fileName, databaseMode)
            setPhotoPreview(photoBitmap)
        }
    }

    fun onSelect(option: String) {
        when (option) {
            ProductDetailsOption.ADD_BARCODE.name -> {
                onAddBarcode(true)
            }

            ProductDetailsOption.ADD_PHOTO.name -> {
                onNavigateToAddPhoto(true)
            }

            ProductDetailsOption.PRINT_QR_CODES.name -> {
                onNavigateToPrintQrCodes(true)
            }

            ProductDetailsOption.EDIT.name -> {
                onNavigateToEditProduct(true)
            }

            ProductDetailsOption.DELETE.name -> {
                onShowDialogWarningDelete(true)
            }
        }
        onShowMenu(false)
    }

    fun onScanBarcode(result: ScanIntentResult) {
        updateProducts(result.contents)
    }

    private fun updateProducts(barcode: String) {
        viewModelScope.launch(Dispatchers.IO) {
            val product = groupProduct.product.copy(barcode = barcode)
            updateProductsUseCase(
                product,
                groupProduct.idList,
                groupProduct.quantity,
                groupProduct.quantity
            )
        }
    }

    fun onShowMenu(bool: Boolean) {
        _state.update {
            it.copy(isMenuVisible = bool)
        }
    }

    fun onNavigateToEditProduct(bool: Boolean) {
        _state.update {
            it.copy(onNavigateToEditProduct = bool)
        }
    }

    fun onNavigateToPrintQrCodes(bool: Boolean) {
        _state.update {
            it.copy(onNavigateToPrintQrCodes = bool)
        }
    }

    fun onNavigateToAddPhoto(bool: Boolean) {
        _state.update {
            it.copy(onNavigateToAddPhoto = bool)
        }
    }

    fun onAddBarcode(bool: Boolean) {
        _state.update {
            it.copy(onAddBarcode = bool)
        }
    }

    fun onShowDialogWarningDelete(bool: Boolean) {
        _state.update {
            it.copy(isDialogWarningDeleteVisible = bool)
        }
    }

    fun onDeleteProduct() {
        onNavigateToMyPantry(true)
        viewModelScope.launch(Dispatchers.IO) {
            deleteProductsUseCase(groupProduct.idList)
            deleteNotificationForProductsUseCase(groupProduct.idList)
        }
    }

    fun onNavigateToMyPantry(bool: Boolean) {
        _state.update {
            it.copy(onNavigateToMyPantry = bool)
        }
    }

    fun onGoToPermissionSettings(bool: Boolean) {
        _state.update {
            it.copy(goToPermissionSettings = bool)
        }
    }

    private fun setPhotoPreview(bitmap: Bitmap?) {
        _state.update {
            it.copy(photoPreview = bitmap)
        }
    }

    fun getScanOptions(): ScanOptions = runBlocking { buildScanOptionsUseCase() }
}
