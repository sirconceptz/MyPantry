package com.hermanowicz.mypantry.navigation.features.productDetails

import androidx.compose.runtime.Composable
import com.hermanowicz.mypantry.navigation.features.productDetails.ui.ProductDetailsScreen

@Composable
fun ProductDetailsRoute(
    onNavigateToEditProducts: (Int) -> Unit,
    openDrawer: () -> Unit
) {
    ProductDetailsScreen(
        openDrawer = openDrawer,
        onClickEditProducts = onNavigateToEditProducts
    )
}
