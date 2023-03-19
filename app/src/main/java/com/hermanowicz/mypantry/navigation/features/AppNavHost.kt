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
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = AppScreens.App.route
    ) {
        composable(route = AppScreens.App.route) {
            MyPantryRoute(navController)
        }
        composable(route = AppScreens.FilterProduct.route) {
            FilterProductRoute(navController)
        }
        composable(route = AppScreens.EditProduct.route) {
            EditProductRoute(navController)
        }
        composable(route = AppScreens.NewProduct.route) {
            NewProductRoute(navController)
        }
        composable(route = AppScreens.OwnCategories.route) {
            OwnCategoriesRoute(navController)
        }
        composable(route = AppScreens.ScanProduct.route) {
            ScanProductRoute(navController)
        }
        composable(route = AppScreens.StorageLocations.route) {
            StorageLocationsRoute(navController)
        }
        composable(route = AppScreens.Settings.route) {
            SettingsRoute(navController)
        }
    }
}