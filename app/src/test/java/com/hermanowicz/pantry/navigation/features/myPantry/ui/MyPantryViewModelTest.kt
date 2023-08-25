package com.hermanowicz.pantry.navigation.features.myPantry.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.data.model.errorAlertSystem.ErrorAlert
import com.hermanowicz.pantry.domain.category.GetDetailsCategoriesUseCase
import com.hermanowicz.pantry.domain.category.GetMainCategoriesUseCase
import com.hermanowicz.pantry.domain.errorAlertSystem.CheckIsErrorWasDisplayedUseCase
import com.hermanowicz.pantry.domain.errorAlertSystem.FetchActiveErrorAlertsUseCase
import com.hermanowicz.pantry.domain.errorAlertSystem.SaveErrorAsDisplayedUseCase
import com.hermanowicz.pantry.domain.product.GetFilteredProductListUseCase
import com.hermanowicz.pantry.domain.product.GetGroupProductListUseCase
import com.hermanowicz.pantry.domain.product.ObserveAllProductsUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.navigation.features.filterProduct.state.FilterProductDataState
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.Taste
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class MyPantryViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: MyPantryViewModel
    private val getGroupProductListUseCase: GetGroupProductListUseCase = mockk()
    private val observeAllProductsUseCase: ObserveAllProductsUseCase = mockk()
    private val getMainCategoriesUseCase: GetMainCategoriesUseCase = mockk()
    private val getDetailsCategoriesUseCase: GetDetailsCategoriesUseCase = mockk()
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase = mockk()
    private val getFilteredProductListUseCase: GetFilteredProductListUseCase = mockk()
    private val fetchActiveErrorAlertsUseCase: FetchActiveErrorAlertsUseCase = mockk()
    private val checkIsErrorWasDisplayedUseCase: CheckIsErrorWasDisplayedUseCase = mockk()
    private val saveErrorAsDisplayedUseCase: SaveErrorAsDisplayedUseCase = mockk()

    private val testDispatcher = UnconfinedTestDispatcher()

    private val testDatabaseMode = DatabaseMode.LOCAL
    private val testErrorAlertList = listOf(ErrorAlert(15, "Error 15", "Test"))
    private val testGroupProductList = listOf(
        GroupProduct(product = Product(id = 1, photoName = "photo1"), quantity = 1),
        GroupProduct(product = Product(id = 2, photoName = "photo2"), quantity = 2),
        GroupProduct(product = Product(id = 3, photoName = "photo3"), quantity = 3)
    )

    @Before
    fun setup() {
        coEvery {
            getGroupProductListUseCase(
                any()
            )
        } returns testGroupProductList
        coEvery { observeDatabaseModeUseCase() } returns flowOf(testDatabaseMode)
        coEvery { fetchActiveErrorAlertsUseCase() } returns testErrorAlertList
        coEvery { checkIsErrorWasDisplayedUseCase(any()) } returns false

        Dispatchers.setMain(testDispatcher)

        viewModel = MyPantryViewModel(
            getGroupProductListUseCase,
            observeAllProductsUseCase,
            getMainCategoriesUseCase,
            getDetailsCategoriesUseCase,
            observeDatabaseModeUseCase,
            getFilteredProductListUseCase,
            fetchActiveErrorAlertsUseCase,
            checkIsErrorWasDisplayedUseCase,
            saveErrorAsDisplayedUseCase
        )
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `onCleanClick should update filterProductDataState with empty FilterProduct`() {
        // When
        viewModel.onCleanClick()

        // Then
        val expectedState = FilterProductDataState()
        assertEquals(expectedState, viewModel.filterProductDataState.value)
    }

    @Test
    fun `onFilterDataChange should update filterProductDataState`() {
        // Given
        val newData = FilterProductDataState(salty = true)

        // When
        viewModel.onFilterTasteSelect(Taste.SALTY.name, true)

        // Then
        assertEquals(newData, viewModel.filterProductDataState.value)
    }
}
