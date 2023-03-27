package com.hermanowicz.mypantry.navigation.features

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hermanowicz.mypantry.components.common.drawer.AppDrawerView
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
        val closeDrawer = {
            scope.launch {
                drawerState.close()
            }
        }

        ModalDrawer(
            drawerState = drawerState,
            drawerShape = RoundedCornerShape(0),
            gesturesEnabled = true,
            drawerContent = {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination?.route ?: ""

                AppDrawerView(selected = currentDestination, onMyPantry = {
                    navController.navigate(AppScreens.MyPantry.route)
                    closeDrawer()
                }, onNewProduct = {
                    navController.navigate(AppScreens.NewProduct.route)
                    closeDrawer()
                }, onOwnCategories = {
                    navController.navigate(AppScreens.OwnCategories.route)
                    closeDrawer()
                }, onStorageLocations = {
                    navController.navigate(AppScreens.StorageLocations.route)
                    closeDrawer()
                }, onSettings = {
                    navController.navigate(AppScreens.Settings.route)
                    closeDrawer()
                }, onScanProduct = {
                    navController.navigate(AppScreens.ScanProduct.route)
                    closeDrawer()
                })
            }
        ) {
            NavHost(
                navController = navController, startDestination = AppScreens.MyPantry.route
            ) {
                composable(route = AppScreens.MyPantry.route) {
                    MyPantryRoute(navController = navController, openDrawer = { openDrawer() })
                }
                composable(route = AppScreens.FilterProduct.route) {
                    FilterProductRoute(navController = navController, openDrawer = { openDrawer() })
                }
                composable(route = "${AppScreens.ProductDetails.route}/{id}") {
                    ProductDetailsRoute(
                        navController = navController,
                        openDrawer = { openDrawer() }
                    )
                }
                composable(route = "${AppScreens.EditProduct.route}/{id}") {
                    EditProductRoute(navController = navController, openDrawer = { openDrawer() })
                }
                composable(route = AppScreens.NewProduct.route) {
                    NewProductRoute(navController = navController, openDrawer = { openDrawer() })
                }
                composable(route = AppScreens.OwnCategories.route) {
                    OwnCategoriesRoute(navController = navController, openDrawer = { openDrawer() })
                }
                composable(route = AppScreens.ScanProduct.route) {
                    ScanProductRoute(navController = navController, openDrawer = { openDrawer() })
                }
                composable(route = AppScreens.StorageLocations.route) {
                    StorageLocationsRoute(
                        navController = navController,
                        openDrawer = { openDrawer() }
                    )
                }
                composable(route = AppScreens.Settings.route) {
                    SettingsRoute(navController = navController, openDrawer = { openDrawer() })
                }
            }
        }
    }
}
