package com.hermanowicz.pantry.components.common.form

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.cards.FilterProductDetailsAttributesCard
import com.hermanowicz.pantry.components.common.cards.ProductDetailsAttributesCard
import com.hermanowicz.pantry.components.common.checkbox.ProductTasteCheckboxes
import com.hermanowicz.pantry.components.common.dropdown.DropdownCard
import com.hermanowicz.pantry.components.common.picker.DatePickerDouble
import com.hermanowicz.pantry.components.common.picker.DatePickerPrimary
import com.hermanowicz.pantry.components.common.radiogroup.ProductTasteRadioGroup
import com.hermanowicz.pantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.pantry.components.common.textfield.TextFieldAndLabelError
import com.hermanowicz.pantry.components.common.textfield.TextFieldDoubleAndLabel
import com.hermanowicz.pantry.navigation.features.filterProduct.state.FilterProductDataState
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.utils.DatePickerData
import com.hermanowicz.pantry.utils.PickerType
import com.hermanowicz.pantry.utils.ProductState
import com.hermanowicz.pantry.utils.enums.Taste

@Composable
fun ProductForm(
    productDataState: ProductState,
    onNameChange: (String) -> Unit,
    showMainCategoryDropdown: (Boolean) -> Unit,
    onMainCategoryChange: (String) -> Unit,
    showDetailCategoryDropdown: (Boolean) -> Unit,
    onDetailCategoryChange: (String) -> Unit,
    showStorageLocationDropdown: (Boolean) -> Unit,
    onStorageLocationChange: (String) -> Unit,
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
    onTasteSelect: (String) -> Unit,
    onCleanTasteRadioGroup: () -> Unit,
    mainCategoryItemList: Map<String, String>,
    detailCategoryItemList: Map<String, String>,
    storageLocationItemList: Map<String, String>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        TextFieldAndLabelError(
            textfieldText = productDataState.name,
            labelText = stringResource(id = R.string.name),
            textEvent = onNameChange,
            placeholder = stringResource(id = R.string.name),
            showError = productDataState.showErrorWrongName,
            errorText = stringResource(id = R.string.error_wrong_name)
        )
        DropdownCard(
            textLeft = stringResource(id = R.string.main_category),
            mapKey = productDataState.mainCategory,
            itemMap = mainCategoryItemList,
            onClick = { showMainCategoryDropdown(true) },
            onChange = onMainCategoryChange,
            visibleDropdown = productDataState.showMainCategoryDropdown,
            onDismiss = { showMainCategoryDropdown(false) }
        )
        DropdownCard(
            textLeft = stringResource(id = R.string.detail_category),
            mapKey = productDataState.detailCategory,
            itemMap = detailCategoryItemList,
            onClick = { showDetailCategoryDropdown(true) },
            onChange = onDetailCategoryChange,
            visibleDropdown = productDataState.showDetailCategoryDropdown,
            onDismiss = { showDetailCategoryDropdown(false) }
        )
        DropdownCard(
            textLeft = stringResource(id = R.string.storage_location),
            mapKey = productDataState.storageLocation,
            itemMap = storageLocationItemList,
            onClick = { showStorageLocationDropdown(true) },
            onChange = onStorageLocationChange,
            visibleDropdown = productDataState.showStorageLocationDropdown,
            onDismiss = { showStorageLocationDropdown(false) }
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
        TextFieldAndLabelError(
            textfieldText = productDataState.quantity,
            labelText = stringResource(id = R.string.quantity),
            textEvent = onQuantityChange,
            placeholder = stringResource(id = R.string.quantity),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            showError = productDataState.showErrorWrongQuantity,
            errorText = stringResource(id = R.string.error_wrong_quantity)
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
        ProductTasteRadioGroup(
            itemList = Taste.toPairList(),
            selectedItem = productDataState.taste,
            onSelect = onTasteSelect,
            onCleanAll = onCleanTasteRadioGroup
        )
    }
}

@Composable
fun FilterProductForm(
    filterProductDataState: FilterProductDataState,
    onNameChange: (String) -> Unit,
    showMainCategoryDropdown: (Boolean) -> Unit,
    onMainCategoryChange: (String) -> Unit,
    showDetailCategoryDropdown: (Boolean) -> Unit,
    onDetailCategoryChange: (String) -> Unit,
    showStorageLocationDropdown: (Boolean) -> Unit,
    onStorageLocationChange: (String) -> Unit,
    onExpirationDateMinChange: (DatePickerData) -> Unit,
    onExpirationDateMaxChange: (DatePickerData) -> Unit,
    onProductionDateMinChange: (DatePickerData) -> Unit,
    onProductionDateMaxChange: (DatePickerData) -> Unit,
    onWeightMinChange: (String) -> Unit,
    onWeightMaxChange: (String) -> Unit,
    onVolumeMinChange: (String) -> Unit,
    onVolumeMaxChange: (String) -> Unit,
    onHealingPropertiesChange: (String) -> Unit,
    onDosageChange: (String) -> Unit,
    onCompositionChange: (String) -> Unit,
    onIsVegeChange: (String) -> Unit,
    onIsBioChange: (String) -> Unit,
    onHasSugarChange: (String) -> Unit,
    onHasSaltChange: (String) -> Unit,
    showIsVegeDropdown: (Boolean) -> Unit,
    showIsBioDropdown: (Boolean) -> Unit,
    showHasSugarDropdown: (Boolean) -> Unit,
    showHasSaltDropdown: (Boolean) -> Unit,
    onTasteSelect: (selected: String, bool: Boolean) -> Unit,
    mainCategoryItemList: Map<String, String>,
    detailCategoryItemList: Map<String, String>,
    storageLocationItemList: Map<String, String>
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.medium)
    ) {
        TextFieldAndLabel(
            textfieldText = filterProductDataState.name,
            labelText = stringResource(id = R.string.name),
            textEvent = onNameChange,
            placeholder = stringResource(id = R.string.name)
        )
        DropdownCard(
            textLeft = stringResource(id = R.string.main_category),
            mapKey = filterProductDataState.mainCategory,
            itemMap = mainCategoryItemList,
            onClick = { showMainCategoryDropdown(true) },
            onChange = onMainCategoryChange,
            visibleDropdown = filterProductDataState.showMainCategoryDropdown,
            onDismiss = { showMainCategoryDropdown(false) }
        )
        DropdownCard(
            textLeft = stringResource(id = R.string.detail_category),
            mapKey = filterProductDataState.detailCategory,
            itemMap = detailCategoryItemList,
            onClick = { showDetailCategoryDropdown(true) },
            onChange = onDetailCategoryChange,
            visibleDropdown = filterProductDataState.showDetailCategoryDropdown,
            onDismiss = { showDetailCategoryDropdown(false) }
        )
        DropdownCard(
            textLeft = stringResource(id = R.string.storage_location),
            mapKey = filterProductDataState.storageLocation,
            itemMap = storageLocationItemList,
            onClick = { showStorageLocationDropdown(true) },
            onChange = onStorageLocationChange,
            visibleDropdown = filterProductDataState.showStorageLocationDropdown,
            onDismiss = { showStorageLocationDropdown(false) }
        )
        DatePickerDouble(
            labelText = stringResource(id = R.string.expiration_date),
            dateLeftToDisplay = filterProductDataState.expirationDateMin,
            dateRightToDisplay = filterProductDataState.expirationDateMax,
            datePickerLeftData = filterProductDataState.expirationDateMinPickerData,
            datePickerRightData = filterProductDataState.expirationDateMaxPickerData,
            onChangeLeftDate = onExpirationDateMinChange,
            onChangeRightDate = onExpirationDateMaxChange
        )
        DatePickerDouble(
            labelText = stringResource(id = R.string.production_date),
            dateLeftToDisplay = filterProductDataState.productionDateMin,
            dateRightToDisplay = filterProductDataState.productionDateMax,
            datePickerLeftData = filterProductDataState.productionDateMinPickerData,
            datePickerRightData = filterProductDataState.productionDateMaxPickerData,
            onChangeLeftDate = onProductionDateMinChange,
            onChangeRightDate = onProductionDateMaxChange
        )
        TextFieldAndLabel(
            textfieldText = filterProductDataState.composition,
            labelText = stringResource(id = R.string.composition),
            textEvent = onCompositionChange,
            placeholder = stringResource(id = R.string.composition)
        )
        TextFieldAndLabel(
            textfieldText = filterProductDataState.healingProperties,
            labelText = stringResource(id = R.string.healing_properties),
            textEvent = onHealingPropertiesChange,
            placeholder = stringResource(id = R.string.healing_properties)
        )
        TextFieldAndLabel(
            textfieldText = filterProductDataState.dosage,
            labelText = stringResource(id = R.string.dosage),
            textEvent = onDosageChange,
            placeholder = stringResource(id = R.string.dosage)
        )
        TextFieldDoubleAndLabel(
            textfieldLeftText = filterProductDataState.weightMin,
            textfieldRightText = filterProductDataState.weightMax,
            labelText = stringResource(id = R.string.weight),
            textLeftEvent = onWeightMinChange,
            textRightEvent = onWeightMaxChange,
            placeholder = stringResource(id = R.string.weight),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        TextFieldDoubleAndLabel(
            textfieldLeftText = filterProductDataState.volumeMin,
            textfieldRightText = filterProductDataState.volumeMax,
            labelText = stringResource(id = R.string.volume),
            textLeftEvent = onVolumeMinChange,
            textRightEvent = onVolumeMaxChange,
            placeholder = stringResource(id = R.string.volume),
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal)
        )
        FilterProductDetailsAttributesCard(
            isVege = filterProductDataState.isVege,
            isBio = filterProductDataState.isBio,
            hasSugar = filterProductDataState.hasSugar,
            hasSalt = filterProductDataState.hasSalt,
            onIsVegeChange = onIsVegeChange,
            onIsBioChange = onIsBioChange,
            onHasSugarChange = onHasSugarChange,
            onHasSaltChange = onHasSaltChange,
            showIsVegeDropdown = showIsVegeDropdown,
            showIsBioDropdown = showIsBioDropdown,
            showHasSugarDropdown = showHasSugarDropdown,
            showHasSaltDropdown = showHasSaltDropdown,
            isVegeDropdownVisible = filterProductDataState.showIsVegeDropdown,
            isBioDropdownVisible = filterProductDataState.showIsBioDropdown,
            hasSugarDropdownVisible = filterProductDataState.showHasSugarDropdown,
            hasSaltDropdownVisible = filterProductDataState.showHasSaltDropdown
        )
        ProductTasteCheckboxes(
            filterProductDataState = filterProductDataState,
            onSelect = onTasteSelect
        )
    }
}
