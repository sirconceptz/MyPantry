package com.hermanowicz.mypantry.navigation.features.scanProduct.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold

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
