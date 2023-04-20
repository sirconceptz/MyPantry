package com.hermanowicz.pantry.navigation.features.storageLocations

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.storageLocations.ui.StorageLocationsScreen
import com.hermanowicz.pantry.navigation.features.storageLocations.ui.StorageLocationsViewModel

@Composable
fun StorageLocationsRoute(
    openDrawer: () -> Unit,
    viewModel: StorageLocationsViewModel
) {
    StorageLocationsScreen(
        openDrawer = openDrawer,
        viewModel = viewModel
    )
}
