package com.hermanowicz.mypantry.navigation.features

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hermanowicz.mypantry.navigation.features.editProduct.EditProductRoute
import com.hermanowicz.mypantry.navigation.features.filterProduct.FilterProductRoute
import com.hermanowicz.mypantry.navigation.features.myPantry.MyPantryRoute
import com.hermanowicz.mypantry.navigation.features.newProduct.NewProductRoute
import com.hermanowicz.mypantry.navigation.features.ownCategories.OwnCategoriesRoute
import com.hermanowicz.mypantry.navigation.features.productDetails.ProductDetailsRoute
import com.hermanowicz.mypantry.navigation.features.scanProduct.ScanProductRoute
import com.hermanowicz.mypantry.navigation.features.settings.SettingsRoute
import com.hermanowicz.mypantry.navigation.features.storageLocations.StorageLocationsRoute
import kotlinx.coroutines.launch
import timber.log.Timber

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Surface(color = MaterialTheme.colors.background) {
        val drawerState = rememberDrawerState(DrawerValue.Closed)
        val scope = rememberCoroutineScope()
        val openDrawer = {
            scope.launch {
                drawerState.open()
            }
        }
        ModalDrawer(
            drawerState = drawerState,
            gesturesEnabled = true,
            drawerContent = {
                LazyColumn() {
                }
            }
        ) {
            NavHost(
                navController = navController,
                startDestination = AppScreens.App.route
            ) {
                composable(route = AppScreens.App.route) {
                    MyPantryRoute(
                        navController = navController,
                        openDrawer = { openDrawer() }
                    )
                }
                composable(route = AppScreens.FilterProduct.route) {
                    FilterProductRoute(
                        navController
                    ) { openDrawer() }
                }
                composable(route = "${AppScreens.ProductDetails.route}/{id}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                    Timber.d("Received id: $productId")
                    ProductDetailsRoute(
                        navController,
                        productId
                    ) { openDrawer() }
                }
                composable(route = "${AppScreens.EditProduct.route}/{id}") { backStackEntry ->
                    val productId = backStackEntry.arguments?.getString("id")?.toInt() ?: 0
                    EditProductRoute(
                        navController, productId,
                        openDrawer = { openDrawer() }
                    )
                }
                composable(route = AppScreens.NewProduct.route) {
                    NewProductRoute(
                        navController
                    ) { openDrawer() }
                }
                composable(route = AppScreens.OwnCategories.route) {
                    OwnCategoriesRoute(
                        navController,
                        openDrawer = { openDrawer() }
                    )
                }
                composable(route = AppScreens.ScanProduct.route) {
                    ScanProductRoute(
                        navController
                    ) { openDrawer() }
                }
                composable(route = AppScreens.StorageLocations.route) {
                    StorageLocationsRoute(
                        navController
                    ) { openDrawer() }
                }
                composable(route = AppScreens.Settings.route) {
                    SettingsRoute(
                        navController
                    ) { openDrawer() }
                }
            }
        }
    }
}
