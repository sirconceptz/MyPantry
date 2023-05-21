package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import javax.inject.Inject

class GetGroupProductListUseCase @Inject constructor() : (List<Product>) -> List<GroupProduct> {
    override fun invoke(products: List<Product>): List<GroupProduct> {
        val groupProductList: MutableList<GroupProduct> = mutableListOf()
        val toAddGroupProductList: MutableList<GroupProduct> = mutableListOf()
        val toRemoveGroupProductList: MutableList<GroupProduct> = mutableListOf()
        for (product in products) {
            var testedGroupProduct: GroupProduct? = getGroupIfOnList(product, groupProductList)
            if (testedGroupProduct != null) {
                testedGroupProduct.idList.add(product.id)
                toRemoveGroupProductList.add(testedGroupProduct)
                testedGroupProduct =
                    testedGroupProduct.copy(quantity = testedGroupProduct.quantity + 1)
                toAddGroupProductList.add(testedGroupProduct)
            } else {
                val newGroupProduct = GroupProduct(product, 1, mutableListOf(product.id))
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
