package com.hermanowicz.mypantry.navigation.features

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hermanowicz.mypantry.navigation.features.editProduct.EditProductRoute
import com.hermanowicz.mypantry.navigation.features.filterProduct.FilterProductRoute
import com.hermanowicz.mypantry.navigation.features.myPantry.MyPantryRoute
import com.hermanowicz.mypantry.navigation.features.ownCategories.OwnCategoriesRoute
import com.hermanowicz.mypantry.navigation.features.scanProduct.ScanProductRoute
import com.hermanowicz.mypantry.navigation.features.settings.SettingsRoute
import com.hermanowicz.mypantry.navigation.features.storageLocations.StorageLocationsRoute

@Composable
fun AppNavHost () {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = MyPantryScreens.MyPantry.route
        ) {
            composable(route = MyPantryScreens.MyPantry.route) {
                MyPantryRoute(navController)
            }
            composable(route = MyPantryScreens.FilterProduct.route) {
                FilterProductRoute(navController)
            }
            composable(route = MyPantryScreens.EditProduct.route) {
                EditProductRoute(navController)
            }
            composable(route = MyPantryScreens.NewProduct.route) {
                NewProductRoute(navController)
            }
            composable(route = MyPantryScreens.OwnCategories.route) {
                OwnCategoriesRoute(navController)
            }
            composable(route = MyPantryScreens.ScanProduct.route) {
                ScanProductRoute(navController)
            }
            composable(route = MyPantryScreens.StorageLocations.route) {
                StorageLocationsRoute(navController)
            }
            composable(route = MyPantryScreens.Settings.route) {
                SettingsRoute(navController)
            }
        }
}