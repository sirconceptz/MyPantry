package com.hermanowicz.pantry.navigation.features.editProduct.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.category.GetDetailCategoriesUseCase
import com.hermanowicz.pantry.domain.category.GetMainCategoriesUseCase
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.product.GetGroupProductByIdUseCase
import com.hermanowicz.pantry.domain.product.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.product.UpdateProductsUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.storageLocation.GetStorageLocationsMapUseCase
import com.hermanowicz.pantry.domain.storageLocation.ObserveAllStorageLocationsUseCase
import com.hermanowicz.pantry.navigation.features.editProduct.state.EditProductDataState
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class EditProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: EditProductViewModel
    private lateinit var observeAllProductsUseCase: ObserveAllProductsUseCase
    private lateinit var getGroupProductByIdUseCase: GetGroupProductByIdUseCase
    private lateinit var updateProductsUseCase: UpdateProductsUseCase
    private lateinit var getMainCategoriesUseCase: GetMainCategoriesUseCase
    private lateinit var getDetailCategoriesUseCase: GetDetailCategoriesUseCase
    private lateinit var getStorageLocationsMapUseCase: GetStorageLocationsMapUseCase
    private lateinit var observeAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase
    private lateinit var observeAllStorageLocationsUseCase: ObserveAllStorageLocationsUseCase
    private lateinit var observeDatabaseModeUseCase: ObserveDatabaseModeUseCase
    private lateinit var savedStateHandle: SavedStateHandle

    private val databaseMode = DatabaseMode.LOCAL
    private val ownCategories = listOf(
        Category(1, "Category1"),
        Category(2, "Category2")
    )
    private val products =
        listOf(Product(id = 1, name = "Product1"), Product(id = 2, name = "Product2"))
    private val groupProduct = GroupProduct(product = Product(id = 1, name = "Product1"))
    private val productId = 1
    private val testDatabaseMode = DatabaseMode.LOCAL
    private val testDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setup() {
        observeAllProductsUseCase = mockk()
        getGroupProductByIdUseCase = mockk()
        updateProductsUseCase = mockk()
        getMainCategoriesUseCase = mockk()
        getDetailCategoriesUseCase = mockk()
        getStorageLocationsMapUseCase = mockk()
        observeAllOwnCategoriesUseCase = mockk()
        observeAllStorageLocationsUseCase = mockk()
        observeDatabaseModeUseCase = mockk()
        savedStateHandle = mockk()

        coEvery { observeDatabaseModeUseCase() } returns flowOf(databaseMode)
        coEvery { observeAllOwnCategoriesUseCase(databaseMode) } returns flowOf(ownCategories)
        coEvery { observeAllProductsUseCase.invoke(DatabaseMode.LOCAL) } returns flowOf(products)
        every { getGroupProductByIdUseCase(productId, products) } returns groupProduct
        every<String?> { savedStateHandle["id"] } returns "1"
        every { observeDatabaseModeUseCase() } returns flowOf(testDatabaseMode)

        viewModel = EditProductViewModel(
            observeAllProductsUseCase,
            getGroupProductByIdUseCase,
            updateProductsUseCase,
            getMainCategoriesUseCase,
            getDetailCategoriesUseCase,
            getStorageLocationsMapUseCase,
            observeAllOwnCategoriesUseCase,
            observeAllStorageLocationsUseCase,
            observeDatabaseModeUseCase,
            savedStateHandle
        )

        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onSaveClick should update showErrorWrongName to true if name length is less than 3`() {
        // Given
        val name = "Pr"

        // When
        viewModel.onNameChange(name)
        viewModel.onSaveClick()

        // Then
        val updatedState = viewModel.productDataState.value
        assert(updatedState.showErrorWrongName)
    }

    @Test
    fun `onSaveClick should update showErrorWrongName to true if name length is greater than 40`() {
        // Given
        val name =
            "This is a very long name for a product that should exceed the maximum allowed length"

        // When
        viewModel.onNameChange(name)
        viewModel.onSaveClick()

        // Then
        val updatedState = viewModel.productDataState.value
        assert(updatedState.showErrorWrongName)
    }

    @Test
    fun `onSaveClick should call updateProducts and cleanErrors if name length is valid`() =
        runBlocking {
            // Given
            val currentState = EditProductDataState(name = "Product1")

            viewModel.productDataState = MutableStateFlow(currentState).asStateFlow()

            // When
            viewModel.onSaveClick()

            // Then
            val updatedState = viewModel.productDataState.value

            assert(!updatedState.showErrorWrongName)
            assert(!updatedState.showErrorWrongQuantity)
        }
}
