package com.hermanowicz.mypantry.navigation.features.myPantry

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.AppScreens
import com.hermanowicz.mypantry.navigation.features.myPantry.ui.MyPantryScreen

@Composable
fun MyPantryRoute(navController: NavHostController, openDrawer: () -> Unit) {
    MyPantryScreen(
        onClickGroupProduct = {
            navController.navigate("${AppScreens.ProductDetails.route}/$it")
        },
        onClickFilterProduct = {
            navController.navigate(AppScreens.FilterProduct.route)
        },
        openDrawer = openDrawer
    )
}
