package com.hermanowicz.pantry.navigation.features.settings

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.settings.ui.SettingsScreen

@Composable
fun SettingsRoute(
    openDrawer: () -> Unit
) {
    SettingsScreen(
        openDrawer = openDrawer
    )
}
