package com.hermanowicz.pantry.navigation.features.newProduct.ui

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.form.NewProductForm
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun NewProductScreen(
    openDrawer: () -> Unit,
    viewModel: NewProductViewModel = hiltViewModel(),
    onNavigateToMyPantry: () -> Unit
) {
    val productDataState by viewModel.productDataState.collectAsState()

    LaunchedEffect(key1 = productDataState.onNavigateToMyPantry) {
        if (productDataState.onNavigateToMyPantry) {
            onNavigateToMyPantry()
            viewModel.onNavigateToMyPantry(false)
        }
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.new_product),
        openDrawer = openDrawer
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                NewProductForm(
                    productDataState,
                    onNameChange = { viewModel.onNameChange(it) },
                    showMainCategoryDropdown = { viewModel.showMainCategoryDropdown(!productDataState.showMainCategoryDropdown) },
                    onMainCategoryChange = { viewModel.onMainCategoryChange(it) },
                    showDetailCategoryDropdown = { viewModel.showDetailCategoryDropdown(!productDataState.showDetailCategoryDropdown) },
                    onDetailCategoryChange = { viewModel.onDetailCategoryChange(it) },
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
                    mainCategoryItemList = viewModel.getMainCategories(),
                    detailCategoryItemList = viewModel.getDetailCategories()
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