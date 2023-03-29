package com.hermanowicz.mypantry.components.common.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.cards.ProductDetailsAttributesCard
import com.hermanowicz.mypantry.components.common.dropdown.DropdownMainCategory
import com.hermanowicz.mypantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.utils.ProductDataState

@Composable
fun ProductForm(
    productDataState: ProductDataState,
    onNameChange: (String) -> Unit,
    showMainCategoryDropdown: (Boolean) -> Unit,
    onMainCategoryChange: (String) -> Unit,
    onExpirationDateChange: (String) -> Unit,
    onProductionDateChange: (String) -> Unit,
    onQuantityChange: (String) -> Unit,
    onCompositionChange: (String) -> Unit,
    onHealingPropertiesChange: (String) -> Unit,
    onDosageChange: (String) -> Unit,
    onWeightChange: (String) -> Unit,
    onVolumeChange: (String) -> Unit,
    onIsVegeChange: (Boolean) -> Unit,
    onIsBioChange: (Boolean) -> Unit,
    onHasSugarChange: (Boolean) -> Unit,
    onHasSaltChange: (Boolean) -> Unit,
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
        DropdownMainCategory(
            textRight = productDataState.mainCategory,
            onClick = { showMainCategoryDropdown(true) },
            onChange = onMainCategoryChange,
            visibleDropdown = productDataState.showMainCategoryDropdown,
            onDismiss = { showMainCategoryDropdown(false) }
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
        ProductDetailsAttributesCard(
            productDataState = productDataState,
            onIsVegeChange = onIsVegeChange,
            onIsBioChange = onIsBioChange,
            onHasSugarChange = onHasSugarChange,
            onHasSaltChange = onHasSaltChange
        )
    }
}
