package com.hermanowicz.mypantry.navigation.features.settings

import androidx.compose.runtime.Composable
import com.hermanowicz.mypantry.navigation.features.settings.ui.SettingsScreen

@Composable
fun SettingsRoute(openDrawer: () -> Unit) {
    SettingsScreen(
        openDrawer = openDrawer
    )
}
