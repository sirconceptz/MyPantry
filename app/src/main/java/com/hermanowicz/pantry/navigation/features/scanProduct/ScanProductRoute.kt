package com.hermanowicz.pantry.navigation.features.scanProduct

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.scanProduct.ui.ScanProductScreen

@Composable
fun ScanProductRoute(openDrawer: () -> Unit) {
    ScanProductScreen(
        openDrawer = openDrawer
    )
}
