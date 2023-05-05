package com.hermanowicz.pantry.navigation.features.myPantry.ui

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.GetGroupProductListUseCase
import com.hermanowicz.pantry.domain.GetGroupProductByIdUseCase
import com.hermanowicz.pantry.domain.ObserveAllProductsUseCase
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryProductsUiState
import io.mockk.every
import io.mockk.mockk
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.flow.flowOf
import org.junit.Test

class MyPantryViewModelTest {

    @Test
    fun `given viewmodel initialized when ui state observed then ui state is Loading`() {
        //       val mockProduct1 by lazy { mockk<Product>() }
//        val mockProduct2 = mockk<Product>()
//        val mockGroupProduct1 = mockk<GroupProduct>()
//        val mockGroupProduct2 = mockk<GroupProduct>()

        val product1 = Product(id = 1, name = "Test 1")
        val product2 = Product(id = 2, name = "Test 2")
        val expectedProductList = listOf(product1, product2)

        val groupProduct1 = GroupProduct(product = product1, quantity = 5)
        val groupProduct2 = GroupProduct(product = product2, quantity = 3)
        val groupsProduct = listOf(groupProduct1, groupProduct2)

//        every { mockProduct1 } returns product1
//        every { mockProduct2 } returns product2
//        every { mockGroupProduct1 } returns groupProduct1
//        every { mockGroupProduct2 } returns groupProduct1

        val mockGetGroupProductListUseCase = mockk<GetGroupProductListUseCase>()
        every { mockGetGroupProductListUseCase.invoke(any()) } returns groupsProduct

        val mockGetGroupProductByIdUseCase = mockk<GetGroupProductByIdUseCase>()
        every { mockGetGroupProductByIdUseCase.invoke(any(), any()) } returns groupProduct1

        val mockObserveAllProductsUseCase = mockk<ObserveAllProductsUseCase>()
        every { mockObserveAllProductsUseCase.invoke() } returns flowOf(expectedProductList)

        // given
        val viewModel =
            MyPantryViewModel(mockGetGroupProductListUseCase, mockObserveAllProductsUseCase)

        // when
        val uiState = viewModel.uiState.value

        // then
        assertTrue(uiState is MyPantryProductsUiState.Loading)
    }
//
//    @Test
//    fun `given fetchProducts called when data loaded then ui state is Loaded`() {
//        // given
//
//        val mockFlow = flow { emit(expectedProductList) }
//        whenever(mockObserveAllProductsUseCase()).thenReturn(mockFlow)
//        whenever(mockGetGroupProductListUseCase(expectedProductList)).thenReturn(mockGroupsProduct)
//
//        val viewModel =
//            MyPantryViewModel(mockGetGroupProductListUseCase, mockObserveAllProductsUseCase)
//
//        // when
//        viewModel.fetchProducts()
//        val uiState = viewModel.uiState.value
//
//        // then
//        assertTrue(uiState is MyPantryUiState.Loaded)
//    }
//
//    @Test
//    fun `given fetchProducts called when loading data then ui state is Loading`() {
//        // given
//        val mockFlow = flow {
//            emit(expectedProductList)
//        }
//        whenever(mockObserveAllProductsUseCase()).thenReturn(mockFlow)
//
//        val viewModel =
//            MyPantryViewModel(mockGetGroupProductListUseCase, mockObserveAllProductsUseCase)
//
//        // when
//        viewModel.fetchProducts()
//        val uiState = viewModel.uiState.value
//
//        // then
//        assertTrue(uiState is MyPantryUiState.Loading)
//    }
//
//    @Test
//    fun `given fetchProducts called when data loading throws exception then ui state is Error`() {
//        // given
//        val mockException = RuntimeException("mock exception")
//        whenever(mockObserveAllProductsUseCase()).thenThrow(mockException)
//
//        val viewModel =
//            MyPantryViewModel(mockGetGroupProductListUseCase, mockObserveAllProductsUseCase)
//
//        // when
//        viewModel.fetchProducts()
//        val uiState = viewModel.uiState.value
//
//        // then
//        assertTrue(uiState is MyPantryUiState.Error)
//    }
}
