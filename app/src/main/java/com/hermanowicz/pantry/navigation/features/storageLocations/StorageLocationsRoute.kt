package com.hermanowicz.pantry.navigation.features.storageLocations

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.storageLocations.ui.StorageLocationsScreen

@Composable
fun StorageLocationsRoute(openDrawer: () -> Unit) {
    StorageLocationsScreen(
        openDrawer = openDrawer
    )
}
