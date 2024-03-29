package com.hermanowicz.pantry.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R

enum class QrCodeSize(val nameResId: Int) {
    BIG(R.string.big),
    SMALL(R.string.small);

    companion object {
        @Composable
        fun toMap(): Map<String, String> {
            val map: MutableMap<String, String> = mutableMapOf()
            enumValues<QrCodeSize>().forEach { category ->
                map[category.name] = stringResource(category.nameResId)
            }
            return map
        }
    }
}
