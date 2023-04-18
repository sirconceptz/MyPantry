package com.hermanowicz.pantry.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R

enum class DatabaseMode(val nameResId: Int) {
    LOCAL(R.string.local),
    ONLINE(R.string.online);

    companion object {
        @Composable
        fun toMap(): Map<String, String> {
            val map: MutableMap<String, String> = mutableMapOf()
            enumValues<DatabaseMode>().forEach { category ->
                map[category.name] = stringResource(category.nameResId)
            }
            return map
        }
    }
}
