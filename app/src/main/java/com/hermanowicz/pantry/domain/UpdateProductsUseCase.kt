package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.ProductRepository
import javax.inject.Inject

class UpdateProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend (Product, List<Int>, Int, Int) -> Unit {
    override suspend fun invoke(
        product: Product,
        productIdList: List<Int>,
        oldQuantity: Int,
        newQuantity: Int
    ) {
        val mutableProducts: MutableList<Product> = mutableListOf()
        for (i in productIdList.indices) {
            mutableProducts.add(product.copy(id = i))
        }
        val idListReversed = productIdList.asReversed()
        val listToDelete: MutableList<Product> = mutableListOf()
        val listToAdd: MutableList<Product> = mutableListOf()
        if (oldQuantity < newQuantity) {
            val diff = newQuantity - oldQuantity
            for (counter in 0 until diff) {
                listToAdd.add(product.copy(id = 0))
            }
        }
        if (oldQuantity > newQuantity) {
            val diff = oldQuantity - newQuantity
            for (counter in 0 until diff) {
                listToDelete.add(product.copy(id = idListReversed[counter]))
                mutableProducts.removeAt(counter)
            }
        }
        productRepository.update(mutableProducts.toList())
        productRepository.delete(listToDelete.map { it.id })
        productRepository.insert(listToAdd)
    }
}
