package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Test

class ObserveAllProductsUseCaseTest {

    private val productRepository: ProductRepository = mock()
    private val useCase = ObserveAllProductsUseCase(productRepository)

    @Test
    fun `test invoke returns observed products from repository`() = runBlocking {
        val product1 = Product(id = 1, barcode = "123", name = "Product A")
        val product2 = Product(id = 2, barcode = "456", name = "Product B")
        val products = listOf(product1, product2)

        whenever(productRepository.observeAll(any())).thenReturn(flowOf(products))

        val observedProducts = useCase(DatabaseMode.LOCAL).toList().flatten()

        assertEquals(products, observedProducts)
    }

    @Test
    fun `test invoke returns empty list when repository returns empty flow`() = runBlocking {
        whenever(productRepository.observeAll(any())).thenReturn(flowOf(emptyList()))

        val observedProducts = useCase(DatabaseMode.LOCAL).toList().flatten()

        assertEquals(emptyList<Product>(), observedProducts)
    }
}
