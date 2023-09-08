package com.hermanowicz.pantry.domain.product.utils

import android.content.Context
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.category.MainCategories

class GetMainCategoryString {
    companion object {
        fun getMainCategoryFromResourcesFromProduct(context: Context, product: Product): String {
            return try {
                context.getString(enumValueOf<MainCategories>(product.mainCategory).nameResId)
            } catch (e: Exception) {
                product.mainCategory
            }
        }
    }
}
