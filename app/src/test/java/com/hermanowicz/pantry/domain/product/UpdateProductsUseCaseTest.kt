package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest
import org.junit.Test
import org.mockito.Mockito.doNothing

class UpdateProductsUseCaseTest {

    private val productRepository: ProductRepository = mock()
    private val useCase = UpdateProductsUseCase(productRepository)

    @Test
    fun `test invoke updates products`() = runTest {
        val updatedProduct = Product(id = 1, name = "Updated Product")
        val productIdList = listOf(1, 2, 3)
        val oldQuantity = 2
        val newQuantity = 4

        doReturn(null).whenever(productRepository).update(any())
        doReturn(null).whenever(productRepository).delete(any())
        doReturn(emptyList<Long>()).whenever(productRepository).insert(any())

        useCase(updatedProduct, productIdList, oldQuantity, newQuantity)

        val updateCaptor = argumentCaptor<List<Product>>()
        verify(productRepository).update(updateCaptor.capture())

        val deleteCaptor = argumentCaptor<List<Int>>()
        verify(productRepository).delete(deleteCaptor.capture())

        val insertCaptor = argumentCaptor<List<Product>>()
        verify(productRepository).insert(insertCaptor.capture())
    }
}
