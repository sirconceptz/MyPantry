package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.PhotoRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import javax.inject.Inject

class UpdatePhotoInProductListUseCase @Inject constructor(
    private val updateProductsUseCase: UpdateProductsUseCase,
    private val photoRepository: PhotoRepository
) : suspend (String, List<Product>, DatabaseMode) -> Unit {
    override suspend fun invoke(
        photoFileName: String,
        products: List<Product>,
        databaseMode: DatabaseMode
    ) {
        val photo: String = if (databaseMode == DatabaseMode.LOCAL) {
            photoFileName
        } else {
            photoRepository.decodePhotoFromGallery(photoFileName)
                ?.let { photoRepository.encodeImageToString(it) }
        } ?: ""
        val product = products[0].copy(photoName = photo)
        val productIdList = products.map { it.id }
        val quantity = products.size
        updateProductsUseCase(product, productIdList, quantity, quantity)
    }
}