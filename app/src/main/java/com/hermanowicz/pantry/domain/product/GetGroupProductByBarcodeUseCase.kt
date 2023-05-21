package com.hermanowicz.pantry.domain.product

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product
import javax.inject.Inject

class GetGroupProductListByBarcodeUseCase @Inject constructor(
    private val getGroupProductListUseCase: GetGroupProductListUseCase
) : (String, List<Product>, String?) -> List<GroupProduct> {
    override fun invoke(
        barcode: String,
        products: List<Product>,
        productName: String?
    ): List<GroupProduct> {
        val allProductsGroupList = getGroupProductListUseCase(products)
        return if (productName == null)
            allProductsGroupList.filter { it.product.barcode == barcode }
        else
            allProductsGroupList.filter { it.product.barcode == barcode && it.product.name == productName }
    }
}