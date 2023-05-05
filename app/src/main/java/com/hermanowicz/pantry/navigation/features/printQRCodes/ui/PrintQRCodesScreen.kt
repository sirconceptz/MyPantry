package com.hermanowicz.pantry.navigation.features.printQRCodes.ui

import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.core.content.ContextCompat.startActivity
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.cards.CardWhiteBgWithBorder
import com.hermanowicz.pantry.components.common.divider.DividerCardInside
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.domain.GoToPermissionSettingsUseCase
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.utils.PdfFile

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun PrintQRCodesScreen(
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: PrintQRCodesViewModel = hiltViewModel(),
    writePermissions: List<String>
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState.onNavigateBack) {
        //onNavigateBack()
        //viewModel.onNavigateBack(false)
    }

    val launcherPrintQrCodes =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { requestedPermissions ->
            var isGranted = true
            for (permission in requestedPermissions) {
                if (!permission.value)
                    isGranted = false
            }
            if (isGranted) {
                viewModel.onPrintCodesPermissionGranted()
            } else {
                viewModel.onGoToPermissionSettings(true)
            }
        }

    val launcherSharePdfDocument =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { requestedPermissions ->
            var isGranted = true
            for (permission in requestedPermissions) {
                if (!permission.value)
                    isGranted = false
            }
            if (isGranted) {
                viewModel.onSharePdfDocumentPermissionGranted()
            } else {
                viewModel.onGoToPermissionSettings(true)
            }
        }

    if (uiState.goToPermissionSettings) {
        GoToPermissionSettingsUseCase.invoke(LocalContext.current)
        viewModel.onGoToPermissionSettings(false)
    }

    if (uiState.navigateToPrintQrCodes) {
        if (uiState.pdfFileName != null) {
            val context = LocalContext.current
            val pdfUri = FileProvider.getUriForFile(
                context,
                "com.hermanowicz.pantry.provider",
                PdfFile.getPdfFile(uiState.pdfFileName!!)
            )
            val pdfDocumentIntent = Intent(Intent.ACTION_VIEW)
            pdfDocumentIntent.setDataAndType(pdfUri, "application/pdf")
            pdfDocumentIntent.flags = Intent.FLAG_ACTIVITY_NO_HISTORY
            pdfDocumentIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            context.startActivity(pdfDocumentIntent)
        }
        viewModel.clearPdfState()
    }

    if (uiState.navigateToSharePdfDocument) {
        if (uiState.pdfFileName != null) {
            val context = LocalContext.current
            val pdfUri = FileProvider.getUriForFile(
                context,
                "com.hermanowicz.pantry.provider",
                PdfFile.getPdfFile(uiState.pdfFileName!!)
            )
            val emailIntent = Intent(Intent.ACTION_SEND)
            emailIntent.type = "plain/text"
            if (pdfUri != null) {
                emailIntent.putExtra(Intent.EXTRA_STREAM, pdfUri)
            }
            context.startActivity(Intent.createChooser(emailIntent, ""))
        }
        viewModel.clearPdfState()
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.print_qr_codes),
        openDrawer = openDrawer,
        actions = {
            IconButton(
                onClick = { launcherPrintQrCodes.launch(writePermissions.toTypedArray()) },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_print),
                        contentDescription = null,
                        tint = Color.White
                    )
                })
        })
    {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium)
        ) {
            item {
                CardWhiteBgWithBorder {
                    Column(modifier = Modifier.padding(LocalSpacing.current.small)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = stringResource(id = R.string.qr_codes_quantity) + ": ")
                            Text(text = uiState.qrCodesQuantity.toString())
                        }
                        DividerCardInside()
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = stringResource(id = R.string.qr_code_size) + ": ")
                            Text(text = stringResource(id = uiState.sizeOfQrCodeNameResId))
                        }
                    }
                }
                Spacer(modifier = Modifier.height(LocalSpacing.current.small))
                ButtonPrimary(text = stringResource(id = R.string.print_qr_codes)) {
                    launcherPrintQrCodes.launch(writePermissions.toTypedArray())
                }
                Spacer(modifier = Modifier.height(LocalSpacing.current.small))
                ButtonPrimary(text = stringResource(id = R.string.share_pdf_document)) {
                    launcherSharePdfDocument.launch(writePermissions.toTypedArray())
                }
            }
        }
    }
}