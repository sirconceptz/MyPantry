package com.hermanowicz.pantry.navigation.features.editProduct

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.editProduct.ui.EditProductScreen

@Composable
fun EditProductRoute(onNavigateToMyPantry: () -> Unit, openDrawer: () -> Unit) {
    EditProductScreen(
        openDrawer = openDrawer,
        onNavigateToMyPantry = onNavigateToMyPantry
    )
}
