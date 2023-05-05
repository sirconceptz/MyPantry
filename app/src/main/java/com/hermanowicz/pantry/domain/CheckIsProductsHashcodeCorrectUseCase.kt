package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Product
import javax.inject.Inject

class CheckIsProductsHashcodeCorrectUseCase @Inject constructor(

) : (Int, String, List<Product>) -> Boolean {
    override fun invoke(id: Int, hashcode: String, products: List<Product>): Boolean {
        var correct = false
        products.forEach { product ->
            if (product.id == id && product.hashCode == hashcode) {
                correct = true
            }
        }
        return correct
    }
}