package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.data.model.Product
import javax.inject.Inject

class GetGroupProductListUseCase @Inject constructor() : (List<Product>) -> List<GroupProduct> {
    override fun invoke(products: List<Product>): List<GroupProduct> {
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
            if (product == groupProduct.product.copy(id = product.id))
                groupProductReturned = groupProduct
        }
        return groupProductReturned
    }
}
