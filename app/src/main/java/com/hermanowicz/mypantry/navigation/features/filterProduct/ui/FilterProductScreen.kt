package com.hermanowicz.mypantry.navigation.features.filterProduct.ui

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold

@Composable
fun FilterProductScreen(openDrawer: () -> Unit) {
    TopBarScaffold(
        topBarText = stringResource(id = R.string.filter_product),
        openDrawer = openDrawer
    ) {
        Text(stringResource(id = R.string.filter_product))
    }
}
