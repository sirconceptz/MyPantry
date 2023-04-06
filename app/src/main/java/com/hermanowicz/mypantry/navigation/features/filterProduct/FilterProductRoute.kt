package com.hermanowicz.mypantry.navigation.features.filterProduct

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.filterProduct.ui.FilterProductScreen

@Composable
fun FilterProductRoute(navController: NavHostController, openDrawer: () -> Unit) {
    FilterProductScreen(
        openDrawer = openDrawer
    )
}
