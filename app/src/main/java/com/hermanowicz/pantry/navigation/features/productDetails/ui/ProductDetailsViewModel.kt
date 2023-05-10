package com.hermanowicz.pantry.navigation.features.productDetails.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.domain.CheckIsProductsHashcodeCorrectUseCase
import com.hermanowicz.pantry.domain.DeleteProductsUseCase
import com.hermanowicz.pantry.domain.FetchDatabaseModeUseCase
import com.hermanowicz.pantry.domain.GetGroupProductByIdUseCase
import com.hermanowicz.pantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.ParseDeprecatedDatabaseProductsUseCase
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsState
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsUiState
import com.hermanowicz.pantry.utils.enums.ProductDetailsOption
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val observeAllProductsUseCase: ObserveAllProductsUseCase,
    private val getGroupProductByIdUseCase: GetGroupProductByIdUseCase,
    private val savedStateHandle: SavedStateHandle,
    private val fetchDatabaseModeUseCase: FetchDatabaseModeUseCase,
    private val checkIsProductsHashcodeCorrectUseCase: CheckIsProductsHashcodeCorrectUseCase,
    private val parseDeprecatedDatabaseProductsUseCase: ParseDeprecatedDatabaseProductsUseCase,
    private val deleteProductsUseCase: DeleteProductsUseCase
) : ViewModel() {
    private val _state = MutableStateFlow(ProductDetailsState())
    val state: StateFlow<ProductDetailsState> = _state

    private val _uiState = MutableStateFlow<ProductDetailsUiState>(ProductDetailsUiState.Empty)
    val uiState: StateFlow<ProductDetailsUiState> = _uiState

    private val idAndHashcode: String = savedStateHandle["idAndHashcode"] ?: ";"
    private val argumentsArray = idAndHashcode.split(";")
    val productId = argumentsArray[0].toInt()
    private val productHashcode = argumentsArray[1]
    var productIdList: List<Int> = emptyList()

    init {
        fetchProducts(productId)
    }

    private fun fetchProducts(productId: Int) {
        _uiState.value = ProductDetailsUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                fetchDatabaseModeUseCase().collect { databaseMode ->
                    observeAllProductsUseCase(databaseMode).collect { products ->
                        val parsedProducts = parseDeprecatedDatabaseProductsUseCase(products)
                        if (checkIsProductsHashcodeCorrectUseCase(
                                productId,
                                productHashcode,
                                products
                            )
                        ) {
                            val groupProduct = getGroupProductByIdUseCase(
                                productId,
                                parsedProducts
                            )
                            productIdList = groupProduct.idList
                            _uiState.value = ProductDetailsUiState.Loaded(
                                ProductDetailsModel(
                                    groupProduct = groupProduct,
                                    loadingVisible = false
                                )
                            )
                        } else {
                            if (!state.value.onNavigateToMyPantry) {
                                _uiState.value = ProductDetailsUiState.Error("No correct product")
                            } else
                                _uiState.value = ProductDetailsUiState.Empty
                        }
                    }
                }
            } catch (e: Exception) {
                _uiState.value = ProductDetailsUiState.Error(e.toString())
            }
        }
    }

    fun onShowMenu(bool: Boolean) {
        _state.update {
            it.copy(
                isMenuVisible = bool
            )
        }
    }

    fun onSelect(option: String) {
        when (option) {
            ProductDetailsOption.ADD_BARCODE.name -> {
                onAddBarcode(true)
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
    }

    fun onNavigateToEditProduct(bool: Boolean) {
        _state.update {
            it.copy(
                onNavigateToEditProduct = bool
            )
        }
    }

    fun onNavigateToPrintQrCodes(bool: Boolean) {
        _state.update {
            it.copy(
                onNavigateToPrintQrCodes = bool
            )
        }
    }

    fun onAddBarcode(bool: Boolean) {
        _state.update {
            it.copy(
                onAddBarcode = bool
            )
        }
    }

    fun onShowDialogWarningDelete(bool: Boolean) {
        _state.update {
            it.copy(
                isDialogWarningDeleteVisible = bool
            )
        }
    }

    fun onDeleteProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteProductsUseCase(productIdList)
        }
        onNavigateToMyPantry(true)
    }

    fun onNavigateToMyPantry(bool: Boolean) {
        _state.update {
            it.copy(
                onNavigateToMyPantry = bool
            )
        }
    }
}