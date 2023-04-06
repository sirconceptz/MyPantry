package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.data.model.Product
import javax.inject.Inject

class GetGroupProductUseCase @Inject constructor() : (Int, List<Product>) -> GroupProduct {
    override fun invoke(productId: Int, products: List<Product>): GroupProduct {
        var product = Product()
        for (mProduct in products) {
            if (mProduct.id == productId)
                product = mProduct
        }
        var groupProductReturned = GroupProduct(product, 0)
        for (mProduct in products) {
            if (product == mProduct.copy(id = product.id))
                groupProductReturned =
                    groupProductReturned.copy(quantity = groupProductReturned.quantity + 1)
        }
        return groupProductReturned
    }
}
