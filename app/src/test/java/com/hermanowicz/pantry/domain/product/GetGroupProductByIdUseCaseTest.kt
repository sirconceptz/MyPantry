package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import junit.framework.TestCase.assertEquals
import org.junit.Test

class GetGroupProductByIdUseCaseTest {

    private val useCase = GetGroupProductByIdUseCase()

    @Test
    fun `test invoke returns correct group product based on provided ID and products`() {
        val product1 = Product(id = 1, name = "Product 1")
        val product2 = Product(id = 2, name = "Product 2")
        val product3 = Product(id = 3, name = "Product 1") // Same name as Product 1
        val products = listOf(product1, product2, product3)

        val groupId = 1 // ID of Product 1
        val groupProduct = useCase(groupId, products)

        assertEquals("Product 1", groupProduct.product.name)
        assertEquals(2, groupProduct.quantity) // Product 1 and Product 3 have the same name
        assertEquals(listOf(1, 3), groupProduct.idList)
    }

    @Test
    fun `test invoke returns group product with 0 quantity for unknown ID`() {
        val product1 = Product(id = 1, name = "Product 1")
        val product2 = Product(id = 2, name = "Product 2")
        val products = listOf(product1, product2)

        val groupId = 3 // Unknown ID
        val groupProduct = useCase(groupId, products)

        assertEquals(Product(), groupProduct.product) // Default empty product
        assertEquals(0, groupProduct.quantity)
        assertEquals(emptyList<Int>(), groupProduct.idList)
    }
}
