package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import javax.inject.Inject

class GetGroupProductUseCase @Inject constructor() : (Int, List<Product>) -> GroupProduct {
    override fun invoke(productId: Int, products: List<Product>): GroupProduct {
        var product = Product()
        products.forEach {
            if (it.id == productId)
                product = it
        }
        var groupProductReturned = GroupProduct(product, 0)
        products.forEach {
            if (product == it.copy(id = product.id))
                groupProductReturned =
                    groupProductReturned.copy(
                        quantity = groupProductReturned.quantity + 1
                    )
            groupProductReturned.idList.add(it.id)
        }
        return groupProductReturned
    }
}