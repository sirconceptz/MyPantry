package com.hermanowicz.pantry.navigation.features.scanProduct.ui

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.cards.CardWhiteBgWithBorder
import com.hermanowicz.pantry.components.common.dialog.DialogTextfield
import com.hermanowicz.pantry.components.common.spacer.SpacerMedium
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.domain.settings.GoToPermissionSettingsUseCase
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.utils.enums.ScannerMethod
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions

@Composable
fun ScanProductScreen(
    openDrawer: () -> Unit,
    onNavigationToProductDetails: (Pair<Int, String>) -> Unit,
    onNavigationToNewProduct: (String) -> Unit,
    cameraPermissions: List<String>,
    viewModel: ScanProductViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    val activityResultLauncher: ActivityResultLauncher<ScanOptions> =
        rememberLauncherForActivityResult(
            contract = ScanContract()
        ) { result ->
            viewModel.setScanResult(result)
        }

    val permissionLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { requestedPermissions ->
            var isGranted = true
            for (permission in requestedPermissions) {
                if (!permission.value) {
                    isGranted = false
                }
            }
            if (isGranted) {
                activityResultLauncher.launch(viewModel.getScanOptions())
            } else {
                viewModel.onGoToPermissionSettings(true)
            }
        }

    if (uiState.onNavigateToProductDetails != null) {
        onNavigationToProductDetails(uiState.onNavigateToProductDetails!!)
        viewModel.onNavigateToProductDetails(null)
    }

    if (uiState.onNavigateToNewProduct != null) {
        if (uiState.onNavigateToNewProduct!!.isNotEmpty()) {
            onNavigationToNewProduct(uiState.onNavigateToNewProduct!!)
        }
        viewModel.onNavigateToNewProduct("")
    }

    if (uiState.goToPermissionSettings) {
        GoToPermissionSettingsUseCase.invoke(LocalContext.current)
        viewModel.onGoToPermissionSettings(false)
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.scan_product),
        openDrawer = openDrawer
    ) {
        if (uiState.showPutBarcodeManuallyDialog) {
            DialogTextfield(
                label = stringResource(id = R.string.put_barcode_manually),
                value = uiState.barcodeManually,
                onPositiveRequest = { viewModel.onNavigateToNewProduct(it) },
                onDismissRequest = { viewModel.onPutBarcodeManually(false) }
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                SpacerMedium()
                Text(
                    text = stringResource(id = R.string.scan_qr_code_to_find_details),
                    fontWeight = FontWeight.Bold
                )
                CardWhiteBgWithBorder {
                    Column(modifier = Modifier.padding(LocalSpacing.current.medium)) {
                        Text(text = stringResource(id = R.string.scan_qr_code_statement))
                        ButtonPrimary(text = stringResource(id = R.string.scan_qr_code)) {
                            viewModel.setScanType(ScannerMethod.SCAN_QR_CODE)
                            permissionLauncher.launch(cameraPermissions.toTypedArray())
                        }
                    }
                }
                SpacerMedium()
            }
            item {
                Text(
                    text = stringResource(id = R.string.scan_barcode_to_add_new_product),
                    fontWeight = FontWeight.Bold
                )
                CardWhiteBgWithBorder {
                    Column(modifier = Modifier.padding(LocalSpacing.current.medium)) {
                        Text(stringResource(id = R.string.scan_barcode_statement))
                        ButtonPrimary(text = stringResource(id = R.string.scan_barcode)) {
                            viewModel.setScanType(ScannerMethod.SCAN_BARCODE)
                            permissionLauncher.launch(cameraPermissions.toTypedArray())
                        }
                    }
                }
                SpacerMedium()
            }
            item {
                Text(
                    text = stringResource(id = R.string.put_barcode_manually_to_add_new_product),
                    fontWeight = FontWeight.Bold
                )
                CardWhiteBgWithBorder {
                    Column(modifier = Modifier.padding(LocalSpacing.current.medium)) {
                        Text(text = stringResource(id = R.string.put_barcode_manually_statement))
                        ButtonPrimary(text = stringResource(id = R.string.put_barcode_manually)) {
                            viewModel.onPutBarcodeManually(true)
                        }
                    }
                }
            }
        }
    }
}
