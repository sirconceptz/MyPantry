package com.hermanowicz.mypantry.navigation.features.storageLocations.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold

@Composable
fun StorageLocationsScreen(openDrawer: () -> Unit) {
    TopBarScaffold(
        topBarText = stringResource(id = R.string.storage_locations),
        openDrawer = openDrawer
    ) {
    }
}
