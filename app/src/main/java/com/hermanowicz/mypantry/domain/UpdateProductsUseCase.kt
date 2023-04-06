package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Product
import com.hermanowicz.mypantry.di.repository.ProductRepository
import javax.inject.Inject

class UpdateProductsUseCase @Inject constructor(
    private val productRepository: ProductRepository
) : suspend (List<Product>, Int, Int) -> Unit {
    override suspend fun invoke(products: List<Product>, oldQuantity: Int, newQuantity: Int) {
        val mutableProducts = products.toMutableList().asReversed()
        val listToDelete: MutableList<Product> = mutableListOf()
        val listToAdd: MutableList<Product> = mutableListOf()
        if (oldQuantity < newQuantity) {
            val diff = newQuantity - oldQuantity
            for (counter in 1..diff) {
                listToAdd.add(mutableProducts[counter].copy(id = 0))
                mutableProducts.removeAt(counter)
            }
        }
        if (oldQuantity > newQuantity) {
            val diff = oldQuantity - newQuantity
            for (counter in 0 until diff) {
                listToDelete.add(mutableProducts[counter])
                mutableProducts.removeAt(counter)
            }
        }
        productRepository.update(mutableProducts.toList())
        productRepository.delete(listToDelete)
        productRepository.insert(listToAdd)
    }
}
