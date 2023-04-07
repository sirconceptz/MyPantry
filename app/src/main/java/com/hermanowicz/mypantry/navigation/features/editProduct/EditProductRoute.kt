package com.hermanowicz.mypantry.navigation.features.editProduct

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.AppScreens
import com.hermanowicz.mypantry.navigation.features.editProduct.ui.EditProductScreen

@Composable
fun EditProductRoute(navController: NavHostController, openDrawer: () -> Unit) {
    EditProductScreen(
        openDrawer = openDrawer,
        onToMyPantry = { navController.navigate(AppScreens.MyPantry.route) }
    )
}
