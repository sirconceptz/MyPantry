package com.hermanowicz.mypantry.navigation.features.myPantry

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.AppScreens
import com.hermanowicz.mypantry.navigation.features.myPantry.ui.MyPantryScreen

@Composable
fun MyPantryRoute(navController: NavHostController) {
    MyPantryScreen(
        onNewProduct = { navController.navigate(AppScreens.NewProduct.route) },
        onOwnCategories = { navController.navigate(AppScreens.OwnCategories.route) },
        onStorageLocations = { navController.navigate(AppScreens.StorageLocations.route) },
        onFilterProduct = { navController.navigate(AppScreens.FilterProduct.route) },
        onEditProduct = { navController.navigate(AppScreens.EditProduct.route) },
        onSettings = { navController.navigate(AppScreens.Settings.route) },
        onScanProduct = { navController.navigate(AppScreens.ScanProduct.route) }
    )
}
