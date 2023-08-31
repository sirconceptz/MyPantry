package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import junit.framework.TestCase.assertEquals
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class GetGroupProductListByBarcodeUseCaseTest {

    private val mockGetGroupProductListUseCase: GetGroupProductListUseCase = mock(GetGroupProductListUseCase::class.java)
    private val useCase = GetGroupProductListByBarcodeUseCase(mockGetGroupProductListUseCase)

    @Test
    fun `test invoke returns group products based on barcode and product name`() {
        val product1 = Product(id = 1, barcode = "123", name = "Product A")
        val product2 = Product(id = 2, barcode = "123", name = "Product B")
        val product3 = Product(id = 3, barcode = "456", name = "Product A")
        val products = listOf(product1, product2, product3)

        `when`(mockGetGroupProductListUseCase.invoke(products)).thenReturn(
            listOf(
                GroupProduct(product = product1, quantity = 1),
                GroupProduct(product = product2, quantity = 2),
                GroupProduct(product = product3, quantity = 3)
            )
        )

        val barcode = "123"
        val productName = "Product B"
        val groupProducts = useCase(barcode, products, productName)

        assertEquals(1, groupProducts.size)
        assertEquals("Product B", groupProducts[0].product.name)
        assertEquals(2, groupProducts[0].quantity)
    }

    @Test
    fun `test invoke returns empty list when no matching products are found`() {
        val product1 = Product(id = 1, barcode = "123", name = "Product A")
        val product2 = Product(id = 2, barcode = "456", name = "Product B")
        val products = listOf(product1, product2)

        `when`(mockGetGroupProductListUseCase.invoke(products)).thenReturn(emptyList())

        val barcode = "789" // Unknown barcode
        val groupProducts = useCase(barcode, products, null)

        assertEquals(0, groupProducts.size)
    }
}
