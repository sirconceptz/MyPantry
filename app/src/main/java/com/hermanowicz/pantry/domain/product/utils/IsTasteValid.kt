package com.hermanowicz.pantry.domain.product.utils

import com.hermanowicz.pantry.data.model.FilterProduct
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.enums.Taste

class IsTasteValid {
    companion object {
        fun isTasteValid(product: Product, filterProduct: FilterProduct): Boolean {
            return ((filterProduct.sweet && product.taste == Taste.SWEET.name) || (filterProduct.sour && product.taste == Taste.SOUR.name) || (filterProduct.sweetAndSour && product.taste == Taste.SWEET_AND_SOUR.name) || (filterProduct.salty && product.taste == Taste.SALTY.name) || (filterProduct.spicy && product.taste == Taste.SPICY.name) || (!filterProduct.sweet && !filterProduct.sour && !filterProduct.sweetAndSour && !filterProduct.salty && !filterProduct.spicy))
        }
    }
}
