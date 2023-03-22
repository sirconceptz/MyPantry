package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.data.model.Product
import timber.log.Timber
import javax.inject.Inject

class GetGroupProductListUseCase @Inject constructor() : (List<Product>) -> List<GroupProduct> {
    override fun invoke(products: List<Product>): List<GroupProduct> {
        Timber.d("Use case get group size: " + products.size)
        val groupProductList: MutableList<GroupProduct> = ArrayList()
        val toAddGroupProductList: MutableList<GroupProduct> = ArrayList()
        val toRemoveGroupProductList: MutableList<GroupProduct> = ArrayList()
        for (product in products) {
            var testedGroupProduct: GroupProduct? = getGroupIfOnList(product, groupProductList)
            if (testedGroupProduct != null) {
                toRemoveGroupProductList.add(testedGroupProduct)
                testedGroupProduct =
                    testedGroupProduct.copy(quantity = testedGroupProduct.quantity + 1)
                toAddGroupProductList.add(testedGroupProduct)
            } else {
                val newGroupProduct = GroupProduct(product, 1)
                toAddGroupProductList.add(newGroupProduct)
            }
            groupProductList.removeAll(toRemoveGroupProductList)
            groupProductList.addAll(toAddGroupProductList)
            toAddGroupProductList.clear()
            toRemoveGroupProductList.clear()
        }

        return groupProductList
    }

    private fun getGroupIfOnList(
        product: Product,
        groupProductList: List<GroupProduct>
    ): GroupProduct? {
        var groupProductReturned: GroupProduct? = null
        for (groupProduct in groupProductList) {
            if (product == groupProduct.product)
                groupProductReturned = groupProduct
        }
        return groupProductReturned
    }
}