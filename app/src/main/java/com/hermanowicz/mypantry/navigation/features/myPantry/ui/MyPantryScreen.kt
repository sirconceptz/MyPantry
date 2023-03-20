package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Button
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.Shapes

@Composable
fun MyPantryScreen(
    viewModel: MyPantryViewModel = viewModel(),
    onNewProductClick: () -> Unit,
    onOwnCategoriesClick: () -> Unit,
    onStorageLocationsClick: () -> Unit,
    onFilterProduct: () -> Unit,
    onEditProduct: () -> Unit,
    onScanProduct: () -> Unit,
    onSettingsClick: () -> Unit
) {
    val products by viewModel.products.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.medium)
    ) {
        products.forEach { groupProduct ->
            item {
                GroupProductItemCard(groupProduct = groupProduct)
            }
        }
        item {
            Text("MyPantry")
        }
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = onNewProductClick) {
                Text("New product")
            }
        }
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = onOwnCategoriesClick) {
                Text("Own categories")
            }
        }
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = onStorageLocationsClick) {
                Text("Storage locations")
            }
        }
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = onFilterProduct) {
                Text("Filter product")
            }
        }
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = onEditProduct) {
                Text("Edit product")
            }
        }
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = onScanProduct) {
                Text("Scan product")
            }
        }
        item {
            Button(modifier = Modifier.fillMaxWidth(), onClick = onSettingsClick) {
                Text("Settings")
            }
        }
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
