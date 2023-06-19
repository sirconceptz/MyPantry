package com.hermanowicz.pantry.navigation.features.addPhoto.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.image.PhotoBox
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.domain.settings.GoToPermissionSettingsUseCase
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.utils.Permissions

@Composable
fun AddPhotoScreen(
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: AddPhotoViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val cameraPermission = Permissions.cameraAndWritePermissions

    val launcherPermissionsAddPhoto =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { requestedPermissions ->
            var isGranted = true
            for (permission in requestedPermissions) {
                if (!permission.value) {
                    isGranted = false
                }
            }
            if (isGranted) {
                viewModel.onClickAddPhoto(true)
            } else {
                viewModel.onGoToPermissionSettings(true)
            }
        }

    val launcherTakePhoto =
        rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { correctlySaved ->
            if (correctlySaved) {
                viewModel.onPhotoSavedCorrectlyInGallery(viewModel.getPhotoFileName())
            }
        }

    LaunchedEffect(key1 = uiState.onClickAddPhoto) {
        if (uiState.onClickAddPhoto) {
            val imageFile = viewModel.createAndGetPhotoFile()
            if (imageFile != null) {
                val uri = FileProvider.getUriForFile(
                    context,
                    "com.hermanowicz.pantry.provider",
                    imageFile
                )
                launcherTakePhoto.launch(uri)
            }
            viewModel.onClickAddPhoto(false)
        }
    }

    LaunchedEffect(key1 = uiState.goToPermissionSettings) {
        if (uiState.goToPermissionSettings) {
            GoToPermissionSettingsUseCase.invoke(context)
            viewModel.onGoToPermissionSettings(false)
        }
    }

    LaunchedEffect(key1 = uiState.onNavigateBack) {
        if (uiState.onNavigateBack) {
            onNavigateBack()
            viewModel.onNavigateBack(false)
        }
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.add_photo),
        openDrawer = openDrawer
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            if (uiState.photoPreview != null) {
                item {
                    PhotoBox(uiState.photoPreview!!)
                    ButtonPrimary(
                        modifier = Modifier.padding(vertical = LocalSpacing.current.medium),
                        text = stringResource(R.string.save),
                        onClick = { viewModel.savePhotoToDatabase() }
                    )
                    ButtonPrimary(
                        modifier = Modifier.padding(vertical = LocalSpacing.current.medium),
                        text = stringResource(R.string.delete_photo),
                        onClick = { viewModel.deletePhoto() }
                    )
                }
            }
            item {
                ButtonPrimary(
                    modifier = Modifier.padding(vertical = LocalSpacing.current.medium),
                    text = stringResource(R.string.take_photo),
                    onClick = { launcherPermissionsAddPhoto.launch(cameraPermission.toTypedArray()) }
                )
            }
        }
    }
}
