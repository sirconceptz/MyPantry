package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import org.junit.Test

class GetProductListByIdsUseCaseTest {

    private val observeAllProductsUseCase: ObserveAllProductsUseCase = mock()
    private val useCase = GetProductListByIdsUseCase(observeAllProductsUseCase)

    @Test
    fun `test invoke returns filtered products based on provided product ID list`() = runBlocking {
        val product1 = Product(id = 1, barcode = "123", name = "Product A")
        val product2 = Product(id = 2, barcode = "456", name = "Product B")
        val product3 = Product(id = 3, barcode = "789", name = "Product C")
        val products = listOf(product1, product2, product3)

        whenever(observeAllProductsUseCase(any())).thenReturn(flowOf(products))

        val filteredProducts = useCase(DatabaseMode.LOCAL, listOf(1, 3)).single()

        assertEquals(listOf(product1, product3), filteredProducts)
    }

    @Test
    fun `test invoke returns empty list when provided product ID list is empty`() = runBlocking {
        whenever(observeAllProductsUseCase(any())).thenReturn(flowOf(emptyList()))

        val filteredProducts = useCase(DatabaseMode.LOCAL, emptyList()).single()

        assertEquals(emptyList<Product>(), filteredProducts)
    }
}
