package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetProductIdListUseCaseTest {

    private val useCase = GetProductIdListUseCase()

    @Test
    fun `test invoke returns list of product IDs based on provided products`() {
        val product1 = Product(id = 1, barcode = "123", name = "Product A")
        val product2 = Product(id = 2, barcode = "456", name = "Product B")
        val product3 = Product(id = 3, barcode = "789", name = "Product C")
        val products = listOf(product1, product2, product3)

        val productIds = useCase(products)

        assertEquals(listOf(1, 2, 3), productIds)
    }

    @Test
    fun `test invoke returns empty list when no products are provided`() {
        val emptyProductsList = emptyList<Product>()

        val productIds = useCase(emptyProductsList)

        assertEquals(emptyList<Int>(), productIds)
    }
}
