package com.hermanowicz.mypantry.navigation.features.settings

import androidx.compose.runtime.Composable
import com.hermanowicz.mypantry.navigation.features.settings.ui.SettingsScreen

@Composable
fun SettingsRoute(
    onNavigateToUserAccount: () -> Unit,
    onNavigateToAuthorInfo: () -> Unit,
    openDrawer: () -> Unit
) {
    SettingsScreen(
        onClickUserAccount = onNavigateToUserAccount,
        openDrawer = openDrawer
    )
}
