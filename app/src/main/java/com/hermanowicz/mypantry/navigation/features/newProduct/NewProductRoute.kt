package com.hermanowicz.mypantry.navigation.features.newProduct

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.newProduct.ui.NewProductScreen

@Composable
fun NewProductRoute(navController: NavHostController, openDrawer: () -> Unit) {
    NewProductScreen(openDrawer)
}
