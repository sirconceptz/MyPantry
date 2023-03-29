package com.hermanowicz.mypantry.navigation.features.storageLocations.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.cards.StorageLocationItemCard
import com.hermanowicz.mypantry.components.common.dialog.DialogAddNewItem
import com.hermanowicz.mypantry.components.common.loading.LoadingDialog
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.mypantry.data.model.StorageLocation
import com.hermanowicz.mypantry.navigation.features.storageLocations.state.StorageLocationsModel
import com.hermanowicz.mypantry.navigation.features.storageLocations.state.StorageLocationsUiState
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import timber.log.Timber

@Composable
fun StorageLocationsScreen(
    openDrawer: () -> Unit,
) {
    val viewModel: StorageLocationsViewModel = hiltViewModel()
    val storageLocationState by viewModel.storageLocationState.collectAsState()
    val storageLocationModel = updateStorageLocationsModel(viewModel)

    if (storageLocationModel.storageLocations.isEmpty() && storageLocationState.isEditMode)
        viewModel.onEditMode(false)

    TopBarScaffold(
        topBarText = stringResource(id = R.string.storage_locations),
        openDrawer = openDrawer,
        actions = {
            IconButton(onClick = { viewModel.onShowDialogAddNewStorageLocation(true) }, content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_item),
                    contentDescription = null,
                    tint = Color.White
                )
            })
            IconButton(
                onClick = { viewModel.onEditMode(!storageLocationState.isEditMode) },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        tint = Color.White
                    )
                }
            )
        }
    ) {
        if (storageLocationState.showDialogAddNewStorageLocation) {
            DialogAddNewItem(
                onDismissRequest = { viewModel.onShowDialogAddNewStorageLocation(false) },
                label = stringResource(id = R.string.add_new_storage_location),
                name = storageLocationState.name,
                description = storageLocationState.description,
                onNameChange = { viewModel.onNameChange(it) },
                onDescriptionChange = { viewModel.onDescriptionChange(it) },
                onAddClick = { viewModel.onClickSaveStorageLocations() }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                ShowStorageLocations(
                    storageLocationList = storageLocationModel.storageLocations,
                    onClickDeleteStorageLocation = {
                        viewModel.onDeleteStorageLocation(it)
                    },
                    isEditMode = storageLocationState.isEditMode
                )
            }
        }
    }
}

@Composable
fun ShowStorageLocations(
    storageLocationList: List<StorageLocation>,
    onClickDeleteStorageLocation: (StorageLocation) -> Unit,
    isEditMode: Boolean
) {
    storageLocationList.forEach { storageLocation ->
        StorageLocationItemCard(storageLocation, isEditMode) { onClickDeleteStorageLocation(it) }
    }
}

@Composable
fun updateStorageLocationsModel(
    viewModel: StorageLocationsViewModel
): StorageLocationsModel {
    when (val state = viewModel.uiState.collectAsState().value) {
        is StorageLocationsUiState.Empty -> {
            Timber.d("Storage Locations UI State - Empty")
            return StorageLocationsModel()
        }
        is StorageLocationsUiState.Loading -> {
            Timber.d("Storage Locations UI State - Loading")
            LoadingDialog()
            return StorageLocationsModel()
        }
        is StorageLocationsUiState.Loaded -> {
            Timber.d("Storage Locations UI State - Success")
            return state.data
        }
        is StorageLocationsUiState.Error -> {
            Timber.d("Storage Locations UI State - Error")
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            return StorageLocationsModel()
        }
    }
}
