package com.hermanowicz.pantry.navigation.features.editProduct.ui

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
import com.hermanowicz.pantry.components.common.form.ProductForm
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun EditProductScreen(
    openDrawer: () -> Unit,
    onNavigateToMyPantry: () -> Unit,
    viewModel: EditProductViewModel = hiltViewModel()
) {
    val productDataState by viewModel.productDataState.collectAsState()

    LaunchedEffect(key1 = productDataState.onNavigateToMyPantry) {
        if (productDataState.onNavigateToMyPantry) {
            onNavigateToMyPantry()
            viewModel.onNavigateToMyPantry(false)
        }
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.edit_product),
        openDrawer = { openDrawer() },
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
                    productDataState,
                    onNameChange = { viewModel.onNameChange(it) },
                    onMainCategoryChange = { viewModel.onMainCategoryChange(it) },
                    showDetailCategoryDropdown = { viewModel.showDetailCategoryDropdown(!productDataState.showDetailCategoryDropdown) },
                    onDetailCategoryChange = { viewModel.onDetailCategoryChange(it) },
                    showStorageLocationDropdown = { viewModel.showStorageLocationDropdown(it) },
                    onExpirationDateChange = { viewModel.onExpirationDateChange(it) },
                    onStorageLocationChange = { viewModel.onStorageLocationChange(it) },
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
                    showMainCategoryDropdown = { viewModel.showMainCategoryDropdown(it) },
                    mainCategoryItemList = viewModel.getMainCategories(),
                    detailCategoryItemList = viewModel.getDetailCategories(),
                    storageLocationItemList = viewModel.getStorageLocations(),
                    onTasteSelect = { viewModel.onTasteSelect(it) },
                    onCleanTasteRadioGroup = { viewModel.onTasteSelect("") }
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
