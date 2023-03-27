package com.hermanowicz.mypantry.navigation.features.editProduct.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.button.ButtonPrimary
import com.hermanowicz.mypantry.components.common.form.ProductForm
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun EditProductScreen(
    openDrawer: () -> Unit,
    viewModel: EditProductViewModel = hiltViewModel()
) {
    val productDataState by viewModel.productDataState.collectAsState()

    TopBarScaffold(
        topBarText = stringResource(id = R.string.edit_product),
        openDrawer = { openDrawer() }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                ProductForm(
                    productDataState,
                    onNameChange = { viewModel.onNameChange(it) },
                    onExpirationDateChange = { viewModel.onExpirationDateChange(it) },
                    onProductionDateChange = { viewModel.onProductionDateChange(it) },
                    onQuantityChange = { viewModel.onQuantityChange(it) },
                    onCompositionChange = { viewModel.onCompositionChange(it) },
                    onHealingPropertiesChange = { viewModel.onHealingPropertiesChange(it) },
                    onDosageChange = { viewModel.onDosageChange(it) },
                    onWeightChange = { viewModel.onWeightChange(it) },
                    onVolumeChange = { viewModel.onVolumeChange(it) },
                    onIsVegeChange = { viewModel.onIsVegeChange(it) },
                    onIsBioChange = { viewModel.onIsBioChange(it) },
                    onHasSugarChange = { viewModel.onHasSugarChange(it) },
                    onHasSaltChange = { viewModel.onHasSaltChange(it) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
                ButtonPrimary(
                    text = stringResource(id = R.string.save_products),
                    onClick = { viewModel.onSaveClick() }
                )
            }
        }
    }
}
