package com.hermanowicz.pantry.navigation.features.productDetails.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.photo.FetchPhotoBitmapUseCase
import com.hermanowicz.pantry.domain.photo.SetPhotoFileUseCase
import com.hermanowicz.pantry.domain.product.CheckIsProductsHashcodeCorrectUseCase
import com.hermanowicz.pantry.domain.product.GetGroupProductByIdUseCase
import com.hermanowicz.pantry.domain.product.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.product.ParseDeprecatedDatabaseProductsUseCase
import com.hermanowicz.pantry.domain.product.UpdateProductsUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsUiState
import com.hermanowicz.pantry.navigation.features.productDetails.ui.ProductDetailsViewModel
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ProductDetailsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ProductDetailsViewModel
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase = mockk()
    private val observeAllProductsUseCase: ObserveAllProductsUseCase = mockk()
    private val observeAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase = mockk()
    private val getGroupProductByIdUseCase: GetGroupProductByIdUseCase = mockk()
    private val updateProductsUseCase: UpdateProductsUseCase = mockk()
    private val parseDeprecatedDatabaseProductsUseCase: ParseDeprecatedDatabaseProductsUseCase = mockk()
    private val checkIsProductsHashcodeCorrectUseCase: CheckIsProductsHashcodeCorrectUseCase = mockk()
    private val fetchPhotoBitmapUseCase: FetchPhotoBitmapUseCase = mockk()
    private val setPhotoFileUseCase: SetPhotoFileUseCase = mockk()
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()

    private val testDispatcher: TestDispatcher = StandardTestDispatcher()
    private val testScope = TestCoroutineScope(testDispatcher)

    @Before
    fun setUp() {
        savedStateHandle["idAndHashcode"] = "123;12345678"
        val parsedProducts = listOf(Product(id = 123, name = "Test Product", hashCode = "12345678"))
        coEvery { observeDatabaseModeUseCase.invoke() } returns flowOf(DatabaseMode.LOCAL)
        coEvery { observeAllProductsUseCase.invoke(any()) } returns flowOf(parsedProducts)
        coEvery { observeAllOwnCategoriesUseCase.invoke(any()) } returns flowOf(emptyList())
        coEvery { parseDeprecatedDatabaseProductsUseCase.invoke(any(), any()) } returns parsedProducts
        coEvery { getGroupProductByIdUseCase.invoke(any(), any()) } returns GroupProduct(
            idList = mutableListOf(123),
            quantity = 1,
            product = parsedProducts[0]
        )
        coEvery { checkIsProductsHashcodeCorrectUseCase.invoke(123, "12345678", parsedProducts) } returns true
        coEvery { setPhotoFileUseCase.invoke(any()) } returns Unit
        coEvery { fetchPhotoBitmapUseCase.invoke(any(), any()) } returns null
        viewModel = ProductDetailsViewModel(
            observeAllProductsUseCase = observeAllProductsUseCase,
            getGroupProductByIdUseCase = getGroupProductByIdUseCase,
            savedStateHandle = savedStateHandle,
            observeDatabaseModeUseCase = observeDatabaseModeUseCase,
            checkIsProductsHashcodeCorrectUseCase = checkIsProductsHashcodeCorrectUseCase,
            parseDeprecatedDatabaseProductsUseCase = parseDeprecatedDatabaseProductsUseCase,
            observeAllOwnCategoriesUseCase = observeAllOwnCategoriesUseCase,
            updateProductsUseCase = updateProductsUseCase,
            setPhotoFileUseCase = setPhotoFileUseCase,
            fetchPhotoBitmapUseCase = fetchPhotoBitmapUseCase,
            deleteProductsUseCase = mockk(),
            deleteNotificationForProductsUseCase = mockk(),
            buildScanOptionsUseCase = mockk()
        )
    }

//    @Test // to fix
//    fun fetchProducts_updates_uiState_with_Loaded_state() {
//        runBlocking {
//            viewModel.fetchProducts(123)
//            viewModel.uiState.collect { uiState ->
//                assert(uiState is ProductDetailsUiState.Loading)
//                assert(uiState is ProductDetailsUiState.Loaded)
//                //val loadedState = uiState as ProductDetailsUiState.Loaded
//                //assert(loadedState.data.groupProduct.product.name == "Test Product")
//            }
//        }
//    }
}
