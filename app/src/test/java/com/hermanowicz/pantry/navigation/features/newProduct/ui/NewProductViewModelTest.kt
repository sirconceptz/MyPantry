package com.hermanowicz.pantry.navigation.features.newProduct.ui

import androidx.lifecycle.SavedStateHandle
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.product.SaveProductsAndCreateNotificationsUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.storageLocation.ObserveAllStorageLocationsUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.nhaarman.mockitokotlin2.mock
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test

class NewProductViewModelTest {

    private lateinit var viewModel: NewProductViewModel
    private val savedStateHandle: SavedStateHandle = SavedStateHandle()
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase = mockk()
    private val observeAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase = mockk()
    private val observeAllStorageLocationsUseCase: ObserveAllStorageLocationsUseCase = mockk()
    private val saveProductsAndCreateNotificationsUseCase: SaveProductsAndCreateNotificationsUseCase = mockk()

    private val databaseMode = DatabaseMode.LOCAL
    private val ownCategories =
        listOf(
            Category(id = 1, name = "Test category 1", description = "Xyz"),
            Category(id = 2, name = "Test category 2", description = "Xyz")
        )
    private val storageLocations =
        listOf(
            StorageLocation(id = 1, name = "Test storage location 1", description = "Xyz"),
            StorageLocation(id = 2, name = "Test storage location 2", description = "Xyz")
        )
    private val mockProductIdList = listOf(1L, 2L)

    @Before
    fun setup() {
        savedStateHandle["barcode"] = "0"
        coEvery { observeDatabaseModeUseCase() } returns flowOf(databaseMode)
        coEvery { observeAllOwnCategoriesUseCase(DatabaseMode.LOCAL) } returns flowOf(ownCategories)
        coEvery { observeAllStorageLocationsUseCase(DatabaseMode.LOCAL) } returns flowOf(storageLocations)
        coEvery { saveProductsAndCreateNotificationsUseCase(any()) } returns mockProductIdList
        viewModel = NewProductViewModel(
            saveProductsAndCreateNotificationsUseCase = saveProductsAndCreateNotificationsUseCase,
            getMainCategoriesUseCase = mock(),
            getDetailCategoriesUseCase = mock(),
            getStorageLocationsMapUseCase = mock(),
            observeAllOwnCategoriesUseCase = observeAllOwnCategoriesUseCase,
            observeDatabaseModeUseCase = observeDatabaseModeUseCase,
            fetchDatabaseModeUseCase = mock(),
            observeAllProductsUseCase = mock(),
            observeAllStorageLocationsUseCase = observeAllStorageLocationsUseCase,
            getGroupProductListByBarcodeUseCase = mock(),
            checkBarcodeIsEmptyUseCase = mock(),
            checkFormatIsNumberUseCase = mock(),
            checkQuantityIsValidUseCase = mock(),
            savedStateHandle = savedStateHandle
        )
    }

    @Test
    fun `fetchOwnCategories updates productDataState`() = runBlocking {
        viewModel.fetchOwnCategories()
        delay(100)
        assert(viewModel.productDataState.value.ownCategories == ownCategories)
    }

    @Test
    fun `onShowDialogMoreThanOneProductWithBarcode updates productDataState`() {
        val groupProducts = listOf(GroupProduct(product = Product(id = 3, name = "Test 3"), quantity = 1), GroupProduct(product = Product(id = 4, name = "Test 4"), quantity = 1))

        viewModel.onShowDialogMoreThanOneProductWithBarcode(true, groupProducts)

        assert(viewModel.productDataState.value.showDialogMoreThanOneProductWithBarcode)
        assert(viewModel.productDataState.value.groupProductsWithBarcode == groupProducts)
    }

    @Test
    fun `onSelectGroupProduct updates selectedProductName`() {
        val productName = "Product1"

        viewModel.onSelectGroupProduct(productName)

        assert(!viewModel.productDataState.value.showDropdownChooseNewProduct)
        assert(viewModel.productDataState.value.selectedProductName == productName)
    }
}
