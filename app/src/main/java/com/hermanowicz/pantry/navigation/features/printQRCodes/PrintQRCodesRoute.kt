package com.hermanowicz.pantry.navigation.features.printQRCodes

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.printQRCodes.ui.PrintQRCodesScreen
import com.hermanowicz.pantry.utils.Permissions

@Composable
fun PrintQRCodesRoute(
    openDrawer: () -> Unit
) {
    PrintQRCodesScreen(
        openDrawer = openDrawer
    )
}