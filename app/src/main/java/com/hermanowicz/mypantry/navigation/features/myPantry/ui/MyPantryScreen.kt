package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.components.common.button.ButtonPrimary
import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryUiState
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.Shapes
import timber.log.Timber

@Composable
fun MyPantryScreen(
    onNewProduct: () -> Unit,
    onOwnCategories: () -> Unit,
    onStorageLocations: () -> Unit,
    onFilterProduct: () -> Unit,
    onEditProduct: () -> Unit,
    onScanProduct: () -> Unit,
    onSettings: () -> Unit
) {
    val uiModel = updateUi()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        item {
            ShowProducts(uiModel)
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
private fun updateUi(): MyPantryModel {
    val viewModel = hiltViewModel<MyPantryViewModel>()

    when (val state = viewModel.uiState.collectAsState().value) {
        is MyPantryUiState.Empty -> {
            Timber.d("Loading products")
            Toast.makeText(LocalContext.current, "Loading", Toast.LENGTH_SHORT).show()
            return MyPantryModel()
        }
        is MyPantryUiState.Loading -> {
            Timber.d("Loading")
            return MyPantryModel()
        }
        is MyPantryUiState.Loaded -> {
            Timber.d("Success")
            return state.data
        }
        is MyPantryUiState.Error -> {
            Timber.d("Error")
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            return MyPantryModel()
        }
    }
}

@Composable
fun ShowProducts(uiModel: MyPantryModel) {
    uiModel.products.forEach { product ->
        GroupProductItemCard(groupProduct = GroupProduct(product, 3))
    }
}

@Composable
fun GroupProductItemCard(
    groupProduct: GroupProduct
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .padding(
                vertical = LocalSpacing.current.small,
                horizontal = LocalSpacing.current.tiny
            )
    ) {
        Column(modifier = Modifier.padding(LocalSpacing.current.small)) {
            Text(text = groupProduct.product.name, fontSize = 20.sp)
            Text(text = "Quantity: " + groupProduct.quantity.toString(), fontSize = 15.sp)
            Row() {
            }
        }
    }
}
