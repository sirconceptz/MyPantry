package com.hermanowicz.pantry.navigation.features.scanProduct

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.scanProduct.ui.ScanProductScreen
import com.hermanowicz.pantry.utils.Permissions

@Composable
fun ScanProductRoute(
    openDrawer: () -> Unit,
    onNavigationToProductDetails: (Pair<Int, String>) -> Unit,
    onNavigationToNewProduct: (String) -> Unit
) {
    ScanProductScreen(
        openDrawer = openDrawer,
        onNavigationToProductDetails = onNavigationToProductDetails,
        onNavigationToNewProduct = onNavigationToNewProduct,
        cameraPermissions = Permissions.cameraPermissions
    )
}
