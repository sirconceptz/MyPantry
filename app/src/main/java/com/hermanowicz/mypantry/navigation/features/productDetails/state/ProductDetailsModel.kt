package com.hermanowicz.mypantry.navigation.features.productDetails.state

import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.data.model.Product

data class ProductDetailsModel(
    var groupProduct: GroupProduct = GroupProduct(Product(), 0),
    var loadingVisible: Boolean = true
)
