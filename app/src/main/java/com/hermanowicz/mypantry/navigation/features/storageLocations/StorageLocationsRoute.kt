package com.hermanowicz.mypantry.navigation.features.storageLocations

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.storageLocations.ui.StorageLocationsScreen

@Composable
fun StorageLocationsRoute(navController: NavHostController, openDrawer: () -> Unit) {
    StorageLocationsScreen(
        openDrawer = openDrawer
    )
}
