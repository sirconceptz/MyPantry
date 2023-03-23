package com.hermanowicz.mypantry.navigation.features.editProduct

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.editProduct.ui.EditProductScreen

@Composable
fun EditProductRoute(navController: NavHostController, productId: Int, openDrawer: () -> Unit) {
    EditProductScreen(productId, openDrawer)
}
