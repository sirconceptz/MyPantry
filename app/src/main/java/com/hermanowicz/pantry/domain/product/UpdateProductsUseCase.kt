package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import javax.inject.Inject

class UpdateProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend (Product, List<Int>, Int, Int) -> Unit {
    override suspend fun invoke(
        updatedProduct: Product,
        productIdList: List<Int>,
        oldQuantity: Int,
        newQuantity: Int
    ) {
        val mutableProducts = mutableListOf<Product>()
        productIdList.forEach { id ->
            if (productIdList.contains(id)) {
                mutableProducts.add(
                    updatedProduct.copy(id = id)
                )
            }
        }
        val idListReversed = productIdList.asReversed()
        val listToDelete: MutableList<Product> = mutableListOf()
        val listToAdd: MutableList<Product> = mutableListOf()
        if (oldQuantity < newQuantity) {
            val diff = newQuantity - oldQuantity
            for (counter in 0 until diff) {
                listToAdd.add(updatedProduct.copy(id = 0))
            }
        }
        if (oldQuantity > newQuantity) {
            val diff = oldQuantity - newQuantity
            for (counter in 0 until diff) {
                listToDelete.add(updatedProduct.copy(id = idListReversed[counter]))
                mutableProducts.removeAt(counter)
            }
        }
        productRepository.update(mutableProducts.toList())
        productRepository.delete(listToDelete.map { it.id })
        productRepository.insert(listToAdd)
    }
}
