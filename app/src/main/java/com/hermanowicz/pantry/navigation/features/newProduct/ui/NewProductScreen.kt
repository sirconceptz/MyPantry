package com.hermanowicz.pantry.navigation.features.newProduct.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
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
import com.hermanowicz.pantry.components.common.dialog.DialogChooseNewProduct
import com.hermanowicz.pantry.components.common.dialog.DialogWarning
import com.hermanowicz.pantry.components.common.form.ProductForm
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.navigation.features.newProduct.state.NewProductState
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun NewProductScreen(
    openDrawer: () -> Unit,
    viewModel: NewProductViewModel = hiltViewModel(),
    onNavigateToMyPantry: () -> Unit,
    onNavigateToPrintQRCodes: (List<Long>) -> Unit
) {
    val state by viewModel.productDataState.collectAsState()

    LaunchedEffect(key1 = state.onNavigateToMyPantry) {
        if (state.onNavigateToMyPantry) {
            onNavigateToMyPantry()
            viewModel.onNavigateToMyPantry(false)
        }
    }

    LaunchedEffect(key1 = state.onNavigateToPrintQRCodes) {
        if (state.onNavigateToPrintQRCodes) {
            onNavigateToPrintQRCodes(state.productIdList)
            viewModel.onNavigateToPrintQRCodes(false)
        }
    }

    Dialogs(state, viewModel)

    TopBarScaffold(
        topBarText = stringResource(id = R.string.new_product),
        openDrawer = openDrawer,
        actions = {
            IconButton(onClick = { viewModel.onSaveClick() }, content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_save),
                    contentDescription = null,
                    tint = Color.White
                )
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                ProductForm(
                    state,
                    onNameChange = { viewModel.onNameChange(it) },
                    showMainCategoryDropdown = { viewModel.showMainCategoryDropdown(!state.showMainCategoryDropdown) },
                    onMainCategoryChange = { viewModel.onMainCategoryChange(it) },
                    showDetailCategoryDropdown = { viewModel.showDetailCategoryDropdown(!state.showDetailCategoryDropdown) },
                    onDetailCategoryChange = { viewModel.onDetailCategoryChange(it) },
                    showStorageLocationDropdown = { viewModel.showStorageLocationDropdown(!state.showStorageLocationDropdown) },
                    onStorageLocationChange = { viewModel.onStorageLocationChange(it) },
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
                    onHasSaltChange = { viewModel.onHasSaltChange(it) },
                    onTasteSelect = { viewModel.onTasteSelect(it) },
                    onCleanTasteRadioGroup = { viewModel.onTasteSelect("") },
                    mainCategoryItemList = viewModel.getMainCategories(),
                    detailCategoryItemList = viewModel.getDetailCategories(),
                    storageLocationItemList = viewModel.getStorageLocations()
                )
            }
            item {
                Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
                ButtonPrimary(
                    text = stringResource(id = R.string.save),
                    onClick = { viewModel.onSaveClick() }
                )
            }
        }
    }
}

@Composable
private fun Dialogs(
    state: NewProductState,
    viewModel: NewProductViewModel
) {
    if (state.showDialogMoreThanOneProductWithBarcode) {
        DialogChooseNewProduct(
            label = stringResource(id = R.string.new_product),
            warning = stringResource(id = R.string.which_product_you_want_to_add),
            selectedProduct = state.selectedProductName,
            dropdownVisible = state.showDropdownChooseNewProduct,
            groupProductList = state.groupProductsWithBarcode.map { it.product.name },
            onSelectGroupProduct = { viewModel.onSelectGroupProduct(it) },
            showDropdown = { viewModel.showChooseNewProductDropdown(it) },
            onPositiveRequest = { viewModel.onPositiveClickProductWithBarcodeDialog() },
            onDismissRequest = {
                viewModel.onShowDialogMoreThanOneProductWithBarcode(
                    false,
                    emptyList()
                )
            }
        )
    }

    if (state.showNavigateToPrintQRCodesDialog) {
        DialogWarning(
            label = stringResource(id = R.string.print_qr_codes),
            warning = stringResource(
                id = R.string.statement_would_you_like_to_print_qr_codes
            ),
            onPositiveRequest = { viewModel.onNavigateToPrintQRCodes(true) },
            onDismissRequest = {
                viewModel.onNavigateToMyPantry(true)
                viewModel.showNavigateToPrintQRCodesDialog(false)
            }
        )
    }
}
