package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.local.model.ProductEntity
import com.hermanowicz.mypantry.data.model.GroupProduct

class GetGroupProductListUseCase : (List<ProductEntity>) -> List<GroupProduct> {
    override fun invoke(products: List<ProductEntity>): List<GroupProduct> {
    val groupProductList: MutableList<GroupProduct> = ArrayList()
        val toAddGroupProductList: MutableList<GroupProduct> = ArrayList()
        val toRemoveGroupProductList: MutableList<GroupProduct> = ArrayList()
        for (product in products) {
            var testedGroupProduct: GroupProduct? = getGroupIfOnList(product, groupProductList)
            if (testedGroupProduct != null) {
                toRemoveGroupProductList.add(testedGroupProduct)
                testedGroupProduct = testedGroupProduct.copy(quantity = testedGroupProduct.quantity + 1)
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
        product: ProductEntity,
        groupProductList: List<GroupProduct>
    ): GroupProduct? {
        var groupProductReturned: GroupProduct? = null
        for (groupProduct in groupProductList) {
            if (product.copy(id = groupProduct.product.id) == groupProduct.product)
                groupProductReturned = groupProduct
        }
        return groupProductReturned
    }
}