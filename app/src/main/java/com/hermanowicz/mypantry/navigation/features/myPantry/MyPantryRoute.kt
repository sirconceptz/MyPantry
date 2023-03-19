package com.hermanowicz.mypantry.navigation.features.myPantry

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.MyPantryScreens
import com.hermanowicz.mypantry.navigation.features.myPantry.ui.MyPantryScreen

@Composable
fun MyPantryRoute(navController: NavHostController) {
    MyPantryScreen(
        onNewProductClick = { navController.navigate(MyPantryScreens.NewProduct.route) },
        onOwnCategoriesClick = { navController.navigate(MyPantryScreens.OwnCategories.route) },
        onStorageLocationsClick = { navController.navigate(MyPantryScreens.StorageLocations.route) },
        onFilterProduct = { navController.navigate(MyPantryScreens.FilterProduct.route) },
        onEditProduct = { navController.navigate(MyPantryScreens.EditProduct.route) },
        onSettingsClick = { navController.navigate(MyPantryScreens.Settings.route) },
        onScanProduct = { navController.navigate(MyPantryScreens.ScanProduct.route) }
    )
}