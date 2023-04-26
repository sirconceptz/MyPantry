package com.hermanowicz.pantry.navigation.features.printQRCodes.ui

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.cards.CardWhiteBgWithBorder
import com.hermanowicz.pantry.components.common.divider.DividerCardInside
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun PrintQRCodesScreen(
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: PrintQRCodesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(key1 = uiState.onNavigateBack) {
        onNavigateBack()
        viewModel.onNavigateBack(false)
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.print_qr_codes),
        openDrawer = openDrawer,
        actions = {
            IconButton(onClick = { viewModel.onClickPrint() }, content = {
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
                    viewModel.onClickPrint()
                }
                Spacer(modifier = Modifier.height(LocalSpacing.current.small))
                ButtonPrimary(text = stringResource(id = R.string.share_pdf_document)) {
                    viewModel.onClickSharePdfDocument()
                }
            }
        }
    }
}