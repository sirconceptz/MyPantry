package com.hermanowicz.mypantry.navigation.features.storageLocations.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.dialog.DialogPrimary
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold

@Composable
fun StorageLocationsScreen(
    openDrawer: () -> Unit
) {
    val viewModel: StorageLocationsViewModel = hiltViewModel()
    val storageLocationState by viewModel.storageLocationState.collectAsState()

    TopBarScaffold(
        topBarText = stringResource(id = R.string.storage_locations),
        openDrawer = openDrawer,
        actions = {
            IconButton(onClick = { viewModel.onClickAddNewStorageLocation(true) }, content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_item),
                    contentDescription = null,
                    tint = Color.White
                )
            })
        }
    ) {
        if (storageLocationState.showDialogAddNewStorageLocation) {
            DialogPrimary(onDismissRequest = { viewModel.onClickAddNewStorageLocation(false) })
        }
    }
}
