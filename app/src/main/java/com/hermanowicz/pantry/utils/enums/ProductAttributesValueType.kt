package com.hermanowicz.pantry.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R

enum class ProductAttributesValueType(val nameResId: Int) {
    YES(R.string.yes),
    NO(R.string.no),
    ALL(R.string.all);

    companion object {
        @Composable
        fun toMap(): Map<String, String> {
            val map: MutableMap<String, String> = mutableMapOf()
            enumValues<ProductAttributesValueType>().forEach { category ->
                map[category.name] = stringResource(category.nameResId)
            }
            return map
        }
    }
}
