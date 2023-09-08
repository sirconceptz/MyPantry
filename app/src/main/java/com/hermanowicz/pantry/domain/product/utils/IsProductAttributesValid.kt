package com.hermanowicz.pantry.domain.product.utils

import com.hermanowicz.pantry.domain.product.utils.CheckProductAttributeState.Companion.checkProductAttributeState
import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType

class IsProductAttributesValid {
    companion object {
        fun isProductAttributesValid(
            productIsBio: Boolean,
            productIsVege: Boolean,
            productHasSugar: Boolean,
            productHasSalt: Boolean,
            filterProductIsBio: String,
            filterProductIsVege: String,
            filterProductHasSugar: String,
            filterProductHasSalt: String
        ): Boolean {
            val isVege = enumValueOf<ProductAttributesValueType>(filterProductIsVege)
            val isBio = enumValueOf<ProductAttributesValueType>(filterProductIsBio)
            val hasSugar = enumValueOf<ProductAttributesValueType>(filterProductHasSugar)
            val hasSalt = enumValueOf<ProductAttributesValueType>(filterProductHasSalt)
            return checkProductAttributeState(isVege, productIsVege) && checkProductAttributeState(
                isBio,
                productIsBio
            ) && checkProductAttributeState(hasSugar, productHasSugar) && checkProductAttributeState(hasSalt, productHasSalt)
        }
    }
}
