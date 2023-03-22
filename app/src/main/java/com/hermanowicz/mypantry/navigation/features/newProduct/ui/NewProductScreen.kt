package com.hermanowicz.mypantry.navigation.features.newProduct.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.button.ButtonPrimary
import com.hermanowicz.mypantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.utils.ProductDataState

@Composable
fun NewProductScreen(
    viewModel: NewProductViewModel = hiltViewModel()
) {
    val productDataState by viewModel.productDataState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        item {
            EditProductForm(
                productDataState,
                onNameChange = { viewModel.onNameChange(it) },
                onExpirationDateChange = { viewModel.onExpirationDateChange(it) },
                onProductionDateChange = { viewModel.onProductionDateChange(it) },
                onQuantityChange = { viewModel.onQuantityChange(it) },
                onCompositionChange = { viewModel.onCompositionChange(it) },
                onHealingPropertiesChange = { viewModel.onHealingPropertiesChange(it) },
                onDosageChange = { viewModel.onDosageChange(it) },
                onWeightChange = { viewModel.onWeightChange(it) },
                onVolumeChange = { viewModel.onVolumeChange(it) }
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

@Composable
fun EditProductForm(
    productDataState: ProductDataState,
    onNameChange: (String) -> Unit,
    onExpirationDateChange: (String) -> Unit,
    onProductionDateChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onCompositionChange: (String) -> Unit,
    onHealingPropertiesChange: (String) -> Unit,
    onDosageChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onVolumeChange: (String) -> Unit
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        TextFieldAndLabel(
            textfieldText = productDataState.name,
            labelText = stringResource(id = R.string.name),
            textEvent = onNameChange,
            placeholder = stringResource(id = R.string.name)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.expirationDate,
            labelText = stringResource(id = R.string.expiration_date),
            textEvent = onExpirationDateChange,
            placeholder = stringResource(id = R.string.expiration_date)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.productionDate,
            labelText = stringResource(id = R.string.production_date),
            textEvent = onProductionDateChange,
            placeholder = stringResource(id = R.string.production_date)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.quantity,
            labelText = stringResource(id = R.string.quantity),
            textEvent = onQuantityChange,
            placeholder = stringResource(id = R.string.quantity),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.composition,
            labelText = stringResource(id = R.string.composition),
            textEvent = onCompositionChange,
            placeholder = stringResource(id = R.string.composition)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.healingProperties,
            labelText = stringResource(id = R.string.healing_properties),
            textEvent = onHealingPropertiesChange,
            placeholder = stringResource(id = R.string.healing_properties)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.dosage,
            labelText = stringResource(id = R.string.dosage),
            textEvent = onDosageChange,
            placeholder = stringResource(id = R.string.dosage)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.weight,
            labelText = stringResource(id = R.string.weight),
            textEvent = onWeightChange,
            placeholder = stringResource(id = R.string.weight),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        TextFieldAndLabel(
            textfieldText = productDataState.volume,
            labelText = stringResource(id = R.string.volume),
            textEvent = onVolumeChange,
            placeholder = stringResource(id = R.string.volume),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
    }
}
