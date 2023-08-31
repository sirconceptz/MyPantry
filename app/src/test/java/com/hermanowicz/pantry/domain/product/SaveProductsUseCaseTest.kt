package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.NotificationRepository
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Test

class SaveProductsUseCaseTest {

    private val productRepository: ProductRepository = mock()
    private val notificationRepository: NotificationRepository = mock()
    private val useCase = SaveProductsUseCase(productRepository, notificationRepository)

    @Test
    fun `test invoke saves products and creates notifications`() = runTest {
        val products = listOf(
            Product(id = 1, name = "Product1"),
            Product(id = 2, name = "Product2")
        )
        val insertedIds = listOf(1L, 2L)

        whenever(productRepository.insert(products)).thenReturn(insertedIds)

        val result = useCase(products)

        verify(productRepository).insert(products)
        verify(notificationRepository).createNotification(products)

        assertEquals(insertedIds, result)
    }
}
