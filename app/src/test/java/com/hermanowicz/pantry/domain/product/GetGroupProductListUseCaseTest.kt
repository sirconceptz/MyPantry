package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetGroupProductListUseCaseTest {

    private val useCase = GetGroupProductListUseCase()

    @Test
    fun `test invoke returns list of group products based on provided products`() {
        val product1 = Product(id = 1, barcode = "123", name = "Product A")
        val product2 = Product(id = 2, barcode = "456", name = "Product B")
        val product3 = Product(id = 3, barcode = "123", name = "Product A")
        val products = listOf(product1, product2, product3)

        val groupProducts = useCase(products)

        assertEquals(2, groupProducts.size)

        val groupProduct1 = groupProducts.find { it.product == product1 }!!
        assertEquals(product1, groupProduct1.product)
        assertEquals(2, groupProduct1.quantity)
        assertEquals(listOf(1, 3), groupProduct1.idList)

        val groupProduct2 = groupProducts.find { it.product == product2 }!!
        assertEquals(product2, groupProduct2.product)
        assertEquals(1, groupProduct2.quantity)
        assertEquals(listOf(2), groupProduct2.idList)
    }

    @Test
    fun `test invoke returns empty list when no products are provided`() {
        val emptyProductsList = emptyList<Product>()

        val groupProducts = useCase(emptyProductsList)

        assertEquals(0, groupProducts.size)
    }
}
