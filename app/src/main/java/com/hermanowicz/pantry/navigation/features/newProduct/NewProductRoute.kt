package com.hermanowicz.pantry.navigation.features.newProduct

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.newProduct.ui.NewProductScreen

@Composable
fun NewProductRoute(onNavigateToMyPantry: () -> Unit, openDrawer: () -> Unit) {
    NewProductScreen(
        openDrawer = openDrawer,
        onNavigateToMyPantry = onNavigateToMyPantry
    )
}
