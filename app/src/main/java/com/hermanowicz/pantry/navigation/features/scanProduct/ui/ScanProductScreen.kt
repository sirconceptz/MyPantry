package com.hermanowicz.pantry.navigation.features.scanProduct.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold

@Composable
fun ScanProductScreen(
    openDrawer: () -> Unit
) {
    TopBarScaffold(
        topBarText = stringResource(id = R.string.scan_product),
        openDrawer = openDrawer
    ) {
    }
}
