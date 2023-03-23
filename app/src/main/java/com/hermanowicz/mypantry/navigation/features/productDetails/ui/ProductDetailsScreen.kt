package com.hermanowicz.mypantry.navigation.features.productDetails.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold

@Composable
fun ProductDetailsScreen(productId: Int, openDrawer: () -> Unit) {
    TopBarScaffold(
        topBarText = stringResource(id = R.string.product_details),
        openDrawer = openDrawer
    ) {
        Column() {
            Text(text = productId.toString())
        }
    }
}
