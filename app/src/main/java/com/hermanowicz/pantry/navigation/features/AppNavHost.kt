package com.hermanowicz.pantry.navigation.features

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DrawerValue
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.rememberDrawerState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.hermanowicz.pantry.components.common.drawer.AppDrawerView
import com.hermanowicz.pantry.navigation.features.editProduct.EditProductRoute
import com.hermanowicz.pantry.navigation.features.filterProduct.FilterProductRoute
import com.hermanowicz.pantry.navigation.features.myPantry.MyPantryRoute
import com.hermanowicz.pantry.navigation.features.myPantry.ui.MyPantryViewModel
import com.hermanowicz.pantry.navigation.features.newProduct.NewProductRoute
import com.hermanowicz.pantry.navigation.features.ownCategories.OwnCategoriesRoute
import com.hermanowicz.pantry.navigation.features.ownCategories.ui.OwnCategoriesViewModel
import com.hermanowicz.pantry.navigation.features.printQRCodes.PrintQRCodesRoute
import com.hermanowicz.pantry.navigation.features.productDetails.ProductDetailsRoute
import com.hermanowicz.pantry.navigation.features.scanProduct.ScanProductRoute
import com.hermanowicz.pantry.navigation.features.settings.SettingsRoute
import com.hermanowicz.pantry.navigation.features.storageLocations.StorageLocationsRoute
import com.hermanowicz.pantry.navigation.features.storageLocations.ui.StorageLocationsViewModel
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
            val myPantryViewModel: MyPantryViewModel = hiltViewModel()
            val ownCategoriesViewModel: OwnCategoriesViewModel = hiltViewModel()
            val storageLocationsViewModel: StorageLocationsViewModel = hiltViewModel()
            NavHost(
                navController = navController, startDestination = AppScreens.MyPantry.route
            ) {
                composable(route = AppScreens.MyPantry.route) {
                    MyPantryRoute(
                        onNavigateToProductDetails = {
                            navController.navigate("${AppScreens.ProductDetails.route}/${it.first};${it.second}")
                        },
                        onNavigateToFilterProduct = {
                            navController.navigate(AppScreens.FilterProduct.route)
                        },
                        openDrawer = { openDrawer() },
                        myPantryViewModel = myPantryViewModel
                    )
                }
                composable(route = AppScreens.FilterProduct.route) {
                    FilterProductRoute(
                        onNavigateBack = { navController.popBackStack() },
                        openDrawer = { openDrawer() },
                        viewModel = myPantryViewModel
                    )
                }
                composable(route = "${AppScreens.ProductDetails.route}/{idAndHashcode}") {
                    ProductDetailsRoute(
                        onNavigateToEditProducts = { navController.navigate("${AppScreens.EditProduct.route}/$it") },
                        openDrawer = { openDrawer() }
                    )
                }
                composable(route = "${AppScreens.EditProduct.route}/{id}") {
                    EditProductRoute(
                        onNavigateToMyPantry = { navController.navigate(AppScreens.MyPantry.route) },
                        openDrawer = { openDrawer() }
                    )
                }
                composable(route = AppScreens.NewProduct.route) {
                    NewProductRoute(
                        onNavigateToMyPantry = { navController.navigate(AppScreens.MyPantry.route) },
                        openDrawer = { openDrawer() },
                        onNavigationPrintQRCodes = {
                            navController.navigate(
                                "${AppScreens.PrintQRCodes.route}/${
                                    it.joinToString(
                                        ";"
                                    )
                                }"
                            )
                        }
                    )
                }
                composable(route = "${AppScreens.PrintQRCodes.route}/{productIdList}") {
                    PrintQRCodesRoute(
                        openDrawer = { openDrawer() },
                        onNavigateBack = { navController.popBackStack() })
                }
                composable(route = AppScreens.OwnCategories.route) {
                    OwnCategoriesRoute(
                        openDrawer = { openDrawer() },
                        viewModel = ownCategoriesViewModel
                    )
                }
                composable(route = AppScreens.ScanProduct.route) {
                    ScanProductRoute(
                        openDrawer = { openDrawer() },
                        onNavigationToProductDetails = {
                            navController.navigate("${AppScreens.ProductDetails.route}/${it.first};${it.second}")
                        },
                        onNavigationToNewProduct = { }
                    )
                }
                composable(route = AppScreens.StorageLocations.route) {
                    StorageLocationsRoute(
                        openDrawer = { openDrawer() },
                        viewModel = storageLocationsViewModel
                    )
                }
                composable(route = AppScreens.Settings.route) {
                    SettingsRoute(
                        observeNewDatabase = {
                            reObserveDatabases(
                                myPantryViewModel,
                                ownCategoriesViewModel,
                                storageLocationsViewModel
                            )
                        },
                        openDrawer = { openDrawer() }
                    )
                }
            }
        }
    }


}

private fun reObserveDatabases(
    myPantryViewModel: MyPantryViewModel,
    ownCategoriesViewModel: OwnCategoriesViewModel,
    storageLocationsViewModel: StorageLocationsViewModel
) {
    myPantryViewModel.observeProducts()
    ownCategoriesViewModel.observeCategories()
    storageLocationsViewModel.observeStorageLocations()
}
