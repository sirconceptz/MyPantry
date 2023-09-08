package com.hermanowicz.pantry.domain.product.utils

import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType

class CheckProductAttributeState {
    companion object {
        fun checkProductAttributeState(
            attributeEnum: ProductAttributesValueType,
            attribute: Boolean
        ): Boolean {
            return attributeEnum == ProductAttributesValueType.ALL || (attributeEnum == ProductAttributesValueType.YES && attribute) || (attributeEnum == ProductAttributesValueType.NO && !attribute)
        }
    }
}
