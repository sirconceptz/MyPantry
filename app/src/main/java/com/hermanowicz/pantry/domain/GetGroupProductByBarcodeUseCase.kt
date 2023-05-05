package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import javax.inject.Inject

class GetGroupProductByBarcodeUseCase @Inject constructor(
) : (String, List<Product>) -> GroupProduct {
    override fun invoke(barcode: String, products: List<Product>): GroupProduct {
        var product = Product()
        products.forEach {
            if (it.barcode == barcode)
                product = it
        }
        var groupProductReturned = GroupProduct(product, 0)
        products.forEach {
            if (product == it.copy(id = product.id, hashCode = product.hashCode)) {
                groupProductReturned =
                    groupProductReturned.copy(
                        quantity = groupProductReturned.quantity + 1
                    )
                groupProductReturned.idList.add(it.id)
            }
        }
        return groupProductReturned
    }
}
