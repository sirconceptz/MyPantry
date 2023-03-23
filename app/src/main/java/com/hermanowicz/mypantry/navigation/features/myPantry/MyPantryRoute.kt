package com.hermanowicz.mypantry.navigation.features.myPantry

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.AppScreens
import com.hermanowicz.mypantry.navigation.features.myPantry.ui.MyPantryScreen
import timber.log.Timber

@Composable
fun MyPantryRoute(navController: NavHostController, openDrawer: () -> Unit) {
    MyPantryScreen(
        onClickGroupProduct = {
            Timber.d("Test navi: $it")
            navController.navigate("${AppScreens.ProductDetails.route}/$it")
        },
        onNewProduct = { navController.navigate(AppScreens.NewProduct.route) },
        onOwnCategories = { navController.navigate(AppScreens.OwnCategories.route) },
        onStorageLocations = { navController.navigate(AppScreens.StorageLocations.route) },
        onFilterProduct = { navController.navigate(AppScreens.FilterProduct.route) },
        onSettings = { navController.navigate(AppScreens.Settings.route) },
        onScanProduct = { navController.navigate(AppScreens.ScanProduct.route) },
        openDrawer = openDrawer
    )
}
