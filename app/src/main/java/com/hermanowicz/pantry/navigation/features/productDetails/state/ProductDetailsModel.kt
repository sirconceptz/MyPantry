package com.hermanowicz.pantry.navigation.features.productDetails.state

import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.Product

data class ProductDetailsModel(
    var groupProduct: GroupProduct = GroupProduct(Product(), 0),
    var loadingVisible: Boolean = true
)
