package com.hermanowicz.pantry.navigation.features.filterProduct.ui

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
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.form.FilterProductForm
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.navigation.features.myPantry.ui.MyPantryViewModel
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun FilterProductScreen(
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit,
    viewModel: MyPantryViewModel
) {
    val filterProductDataState by viewModel.filterProductDataState.collectAsState()

    LaunchedEffect(key1 = filterProductDataState.onNavigateBack) {
        if (filterProductDataState.onNavigateBack) {
            onNavigateBack()
            viewModel.onNavigateBack(false)
        }
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.filter_product),
        openDrawer = openDrawer
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                FilterProductForm(
                    filterProductDataState = filterProductDataState,
                    onNameChange = { viewModel.onFilterNameChange(it) },
                    onMainCategoryChange = { viewModel.onFilterMainCategoryChange(it) },
                    showDetailCategoryDropdown = { viewModel.showFilterDetailCategoryDropdown(!filterProductDataState.showDetailCategoryDropdown) },
                    onExpirationDateMinChange = { viewModel.onFilterExpirationDateMinChange(it) },
                    onExpirationDateMaxChange = { viewModel.onFilterExpirationDateMaxChange(it) },
                    onProductionDateMinChange = { viewModel.onFilterProductionDateMinChange(it) },
                    onProductionDateMaxChange = { viewModel.onFilterProductionDateMaxChange(it) },
                    onDetailCategoryChange = { viewModel.onFilterDetailCategoryChange(it) },
                    onWeightMinChange = { viewModel.onFilterWeightMinChange(it) },
                    onWeightMaxChange = { viewModel.onFilterWeightMaxChange(it) },
                    onVolumeMinChange = { viewModel.onFilterVolumeMinChange(it) },
                    onVolumeMaxChange = { viewModel.onFilterVolumeMaxChange(it) },
                    onHealingPropertiesChange = { viewModel.onFilterHealingPropertiesChange(it) },
                    onDosageChange = { viewModel.onFilterDosageChange(it) },
                    onCompositionChange = { viewModel.onFilterCompositionChange(it) },
                    onIsVegeChange = { viewModel.onFilterIsVegeChange(it) },
                    onIsBioChange = { viewModel.onFilterIsBioChange(it) },
                    onHasSugarChange = { viewModel.onFilterHasSugarChange(it) },
                    onHasSaltChange = { viewModel.onFilterHasSaltChange(it) },
                    onTasteSelect = { taste, bool -> viewModel.onFilterTasteSelect(taste, bool) },
                    showMainCategoryDropdown = { viewModel.showFilterMainCategoryDropdown(it) },
                    mainCategoryItemList = viewModel.getFilterMainCategories(),
                    detailCategoryItemList = viewModel.getFilterDetailCategories(),
                    showIsVegeDropdown = { viewModel.showFilterIsVegeDropdown(it) },
                    showIsBioDropdown = { viewModel.showFilterIsBioDropdown(it) },
                    showHasSugarDropdown = { viewModel.showFilterHasSugarDropdown(it) },
                    showHasSaltDropdown = { viewModel.showFilterHasSaltDropdown(it) }
                )
            }
            item {
                Spacer(modifier = Modifier.height(LocalSpacing.current.medium))
                ButtonPrimary(
                    text = stringResource(id = R.string.search),
                    onClick = { viewModel.onSearchClick() }
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.clear),
                    onClick = { viewModel.onCleanClick() }
                )
            }
        }
    }
}
