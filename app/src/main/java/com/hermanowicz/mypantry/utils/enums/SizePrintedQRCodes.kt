package com.hermanowicz.mypantry.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R

enum class SizePrintedQRCodes(val nameResId: Int) {
    BIG(R.string.big),
    SMALL(R.string.small);

    companion object {
        @Composable
        fun toMap(): Map<String, String> {
            val map: MutableMap<String, String> = mutableMapOf()
            enumValues<SizePrintedQRCodes>().forEach { category ->
                map[category.name] = stringResource(category.nameResId)
            }
            return map
        }
    }
}
