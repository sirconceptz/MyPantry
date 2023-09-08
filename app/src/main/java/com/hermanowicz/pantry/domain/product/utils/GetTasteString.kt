package com.hermanowicz.pantry.domain.product.utils

import android.content.Context
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.enums.Taste

class GetTasteString {
    companion object {
        fun getTasteStringFromResourcesForProduct(context: Context, product: Product): String {
            return try {
                context.getString(enumValueOf<Taste>(product.taste).nameResId)
            } catch (e: Exception) {
                product.taste
            }
        }
    }
}
