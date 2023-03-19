package com.hermanowicz.mypantry.navigation.features.myPantry

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.AppScreens
import com.hermanowicz.mypantry.navigation.features.myPantry.ui.MyPantryScreen

@Composable
fun MyPantryRoute(navController: NavHostController) {
    MyPantryScreen(
        onNewProductClick = { navController.navigate(AppScreens.NewProduct.route) },
        onOwnCategoriesClick = { navController.navigate(AppScreens.OwnCategories.route) },
        onStorageLocationsClick = { navController.navigate(AppScreens.StorageLocations.route) },
        onFilterProduct = { navController.navigate(AppScreens.FilterProduct.route) },
        onEditProduct = { navController.navigate(AppScreens.EditProduct.route) },
        onSettingsClick = { navController.navigate(AppScreens.Settings.route) },
        onScanProduct = { navController.navigate(AppScreens.ScanProduct.route) }
    )
}