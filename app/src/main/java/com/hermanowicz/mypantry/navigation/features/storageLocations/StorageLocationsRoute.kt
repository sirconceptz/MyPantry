package com.hermanowicz.mypantry.navigation.features.storageLocations

import androidx.compose.runtime.Composable
import com.hermanowicz.mypantry.navigation.features.storageLocations.ui.StorageLocationsScreen

@Composable
fun StorageLocationsRoute(openDrawer: () -> Unit) {
    StorageLocationsScreen(
        openDrawer = openDrawer
    )
}
