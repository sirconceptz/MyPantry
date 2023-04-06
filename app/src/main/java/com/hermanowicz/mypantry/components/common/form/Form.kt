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
import com.hermanowicz.mypantry.components.common.dropdown.DropdownPrimary
import com.hermanowicz.mypantry.components.common.picker.DatePickerPrimary
import com.hermanowicz.mypantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.mypantry.navigation.features.editProduct.state.EditProductDataState
import com.hermanowicz.mypantry.navigation.features.newProduct.state.NewProductDataState
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.utils.DatePickerData
import com.hermanowicz.mypantry.utils.PickerType

@Composable
fun NewProductForm(
    productDataState: NewProductDataState,
    onNameChange: (String) -> Unit,
    showMainCategoryDropdown: (Boolean) -> Unit,
    onMainCategoryChange: (String) -> Unit,
    showDetailCategoryDropdown: (Boolean) -> Unit,
    onDetailCategoryChange: (String) -> Unit,
    onExpirationDateChange: (DatePickerData) -> Unit,
    onProductionDateChange: (DatePickerData) -> Unit,
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
    mainCategoryItemList: Map<String, String>,
    detailCategoryItemList: Map<String, String>
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
        DropdownPrimary(
            textLeft = stringResource(id = R.string.main_category),
            mapKey = productDataState.mainCategory,
            itemList = mainCategoryItemList,
            onClick = { showMainCategoryDropdown(true) },
            onChange = onMainCategoryChange,
            visibleDropdown = productDataState.showMainCategoryDropdown,
            onDismiss = { showMainCategoryDropdown(false) }
        )
        DropdownPrimary(
            textLeft = stringResource(id = R.string.detail_category),
            mapKey = productDataState.detailCategory,
            itemList = detailCategoryItemList,
            onClick = { showDetailCategoryDropdown(true) },
            onChange = onDetailCategoryChange,
            visibleDropdown = productDataState.showDetailCategoryDropdown,
            onDismiss = { showDetailCategoryDropdown(false) }
        )
        DatePickerPrimary(
            labelText = stringResource(id = R.string.expiration_date),
            dateToDisplay = productDataState.expirationDate,
            datePickerData = productDataState.expirationDatePickerData,
            onChangeDate = onExpirationDateChange,
            pickerType = PickerType.ALL
        )
        DatePickerPrimary(
            labelText = stringResource(id = R.string.production_date),
            dateToDisplay = productDataState.productionDate,
            datePickerData = productDataState.productionDatePickerData,
            onChangeDate = onProductionDateChange,
            pickerType = PickerType.ALL
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
            isVege = productDataState.isVege,
            isBio = productDataState.isBio,
            hasSugar = productDataState.hasSugar,
            hasSalt = productDataState.hasSalt,
            onIsVegeChange = onIsVegeChange,
            onIsBioChange = onIsBioChange,
            onHasSugarChange = onHasSugarChange,
            onHasSaltChange = onHasSaltChange
        )
    }
}

@Composable
fun EditProductForm(
    productDataState: EditProductDataState,
    onNameChange: (String) -> Unit,
    showMainCategoryDropdown: (Boolean) -> Unit,
    onMainCategoryChange: (String) -> Unit,
    showDetailCategoryDropdown: (Boolean) -> Unit,
    onDetailCategoryChange: (String) -> Unit,
    onExpirationDateChange: (DatePickerData) -> Unit,
    onProductionDateChange: (DatePickerData) -> Unit,
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
    mainCategoryItemList: Map<String, String>,
    detailCategoryItemList: Map<String, String>
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
        DropdownPrimary(
            textLeft = stringResource(id = R.string.main_category),
            mapKey = productDataState.mainCategory,
            itemList = mainCategoryItemList,
            onClick = { showMainCategoryDropdown(true) },
            onChange = onMainCategoryChange,
            visibleDropdown = productDataState.showMainCategoryDropdown,
            onDismiss = { showMainCategoryDropdown(false) }
        )
        DropdownPrimary(
            textLeft = stringResource(id = R.string.detail_category),
            mapKey = productDataState.detailCategory,
            itemList = detailCategoryItemList,
            onClick = { showDetailCategoryDropdown(true) },
            onChange = onDetailCategoryChange,
            visibleDropdown = productDataState.showDetailCategoryDropdown,
            onDismiss = { showDetailCategoryDropdown(false) }
        )
        DatePickerPrimary(
            labelText = stringResource(id = R.string.expiration_date),
            dateToDisplay = productDataState.expirationDate,
            datePickerData = productDataState.expirationDatePickerData,
            onChangeDate = onExpirationDateChange,
            pickerType = PickerType.ALL
        )
        DatePickerPrimary(
            labelText = stringResource(id = R.string.production_date),
            dateToDisplay = productDataState.productionDate,
            datePickerData = productDataState.productionDatePickerData,
            onChangeDate = onProductionDateChange,
            pickerType = PickerType.ALL
        )
        TextFieldAndLabel(
            textfieldText = productDataState.newQuantity,
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
            isVege = productDataState.isVege,
            isBio = productDataState.isBio,
            hasSugar = productDataState.hasSugar,
            hasSalt = productDataState.hasSalt,
            onIsVegeChange = onIsVegeChange,
            onIsBioChange = onIsBioChange,
            onHasSugarChange = onHasSugarChange,
            onHasSaltChange = onHasSaltChange
        )
    }
}
