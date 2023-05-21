package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.Product
import javax.inject.Inject

class GetProductIdListUseCase @Inject constructor() : (List<Product>) -> (List<Int>) {
    override fun invoke(products: List<Product>): List<Int> {
        val idList = mutableListOf<Int>()
        products.forEach { idList.add(it.id) }
        return idList.toList()
    }
}