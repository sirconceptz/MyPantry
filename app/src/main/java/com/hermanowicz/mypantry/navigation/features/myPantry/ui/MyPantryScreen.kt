package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.components.common.button.ButtonPrimary
import com.hermanowicz.mypantry.components.common.cards.GroupProductItemCard
import com.hermanowicz.mypantry.components.common.loading.LoadingDialog
import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryUiState
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import timber.log.Timber

@Composable
fun MyPantryScreen(
    viewModel: MyPantryViewModel = hiltViewModel(),
    onNewProduct: () -> Unit,
    onOwnCategories: () -> Unit,
    onStorageLocations: () -> Unit,
    onFilterProduct: () -> Unit,
    onEditProduct: () -> Unit,
    onScanProduct: () -> Unit,
    onSettings: () -> Unit
) {
    val uiModel = updateUi(viewModel)

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        item {
            ShowProducts(uiModel.groupsProduct)
        }
        item {
            Text("MyPantry")
        }
        item {
            ButtonPrimary(text = "New product", onNewProduct)
        }
        item {
            ButtonPrimary(text = "Own categories", onOwnCategories)
        }
        item {
            ButtonPrimary(text = "Storage locations", onStorageLocations)
        }
        item {
            ButtonPrimary(text = "Filter product", onFilterProduct)
        }
        item {
            ButtonPrimary(text = "Edit product", onEditProduct)
        }
        item {
            ButtonPrimary(text = "Scan product", onScanProduct)
        }
        item {
            ButtonPrimary(text = "Settings", onSettings)
        }
    }
}

@Composable
private fun updateUi(
    viewModel: MyPantryViewModel
): MyPantryModel {
    when (val state = viewModel.uiState.collectAsState().value) {
        is MyPantryUiState.Empty -> {
            Timber.d("My Pantry UI State - Empty")
            return MyPantryModel()
        }
        is MyPantryUiState.Loading -> {
            Timber.d("My Pantry UI State - Loading")
            LoadingDialog()
            return MyPantryModel()
        }
        is MyPantryUiState.Loaded -> {
            Timber.d("My Pantry UI State - Success")
            return state.data
        }
        is MyPantryUiState.Error -> {
            Timber.d("My Pantry UI State - Error")
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            return MyPantryModel()
        }
    }
}

@Composable
fun ShowProducts(groupsProduct: List<GroupProduct>) {
    groupsProduct.forEach { groupProduct ->
        GroupProductItemCard(groupProduct = groupProduct)
    }
}
