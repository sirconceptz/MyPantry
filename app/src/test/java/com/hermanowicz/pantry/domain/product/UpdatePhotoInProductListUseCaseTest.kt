package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.PhotoRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.nhaarman.mockitokotlin2.any
import org.junit.Test
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.test.runTest

class UpdatePhotoInProductListUseCaseTest {

    private val updateProductsUseCase: UpdateProductsUseCase = mock()
    private val photoRepository: PhotoRepository = mock()
    private val useCase = UpdatePhotoInProductListUseCase(updateProductsUseCase, photoRepository)

    @Test
    fun `test invoke updates product photo in local mode`() = runTest {
        val photoFileName = "photo.jpg"
        val products = listOf(Product(id = 1, name = "Product1"), Product(id = 2, name = "Product2"))
        val databaseMode = DatabaseMode.LOCAL

        useCase(photoFileName, products, databaseMode)

        verify(updateProductsUseCase).invoke(any(), any(), any(), any())
    }

    @Test
    fun `test invoke updates product photo in remote mode`() = runTest {
        val photoFileName = "photo.jpg"
        val products = listOf(Product(id = 1, name = "Product1"), Product(id = 2, name = "Product2"))
        val databaseMode = DatabaseMode.ONLINE
        val encodedPhoto = "encodedPhoto"

        whenever(photoRepository.decodePhotoFromGallery(photoFileName)).thenReturn(mock())
        whenever(photoRepository.encodeImageToString(any())).thenReturn(encodedPhoto)

        useCase(photoFileName, products, databaseMode)

        verify(updateProductsUseCase).invoke(any(), any(), any(), any())
    }
}
