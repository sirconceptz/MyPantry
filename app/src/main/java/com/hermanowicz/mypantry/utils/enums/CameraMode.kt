package com.hermanowicz.mypantry.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R

enum class CameraMode(val nameResId: Int) {
    REAR(R.string.rear_camera),
    FRONT(R.string.front_camera);

    companion object {
        @Composable
        fun toMap(): Map<String, String> {
            val map: MutableMap<String, String> = mutableMapOf()
            enumValues<CameraMode>().forEach { category ->
                map[category.name] = stringResource(category.nameResId)
            }
            return map
        }
    }
}
