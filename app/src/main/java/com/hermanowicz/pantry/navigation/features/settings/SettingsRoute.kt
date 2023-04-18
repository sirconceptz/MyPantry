package com.hermanowicz.pantry.navigation.features.settings

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.settings.ui.SettingsScreen

@Composable
fun SettingsRoute(
    onNavigateToUserAccount: () -> Unit,
    openDrawer: () -> Unit
) {
    SettingsScreen(
        onClickUserAccount = onNavigateToUserAccount,
        openDrawer = openDrawer
    )
}
