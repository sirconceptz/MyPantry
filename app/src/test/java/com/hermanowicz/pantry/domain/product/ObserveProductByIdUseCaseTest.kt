package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.test.runTest
import org.junit.Test

class ObserveProductByIdUseCaseTest {

    private val productRepository: ProductRepository = mock()
    private val useCase = ObserveProductByIdUseCase(productRepository)

    @Test
    fun `test invoke returns observed product from repository`() = runTest {
        val productId = 1
        val product = Product(id = productId, barcode = "123", name = "Product A")

        whenever(productRepository.observeById(any(), any())).thenReturn(flowOf(product))

        val observedProduct = useCase(productId, DatabaseMode.LOCAL).single()

        assertEquals(product, observedProduct)
    }
}
