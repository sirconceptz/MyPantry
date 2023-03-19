package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.Text
import androidx.compose.runtime.Composable

@Composable
fun MyPantryScreen(
    onNewProductClick: () -> Unit,
    onOwnCategoriesClick: () -> Unit,
    onStorageLocationsClick: () -> Unit,
    onFilterProduct: () -> Unit,
    onEditProduct: () -> Unit,
    onScanProduct: () -> Unit,
    onSettingsClick: () -> Unit
) {
    Column() {
        Text("MyPantry")
        Button(onClick = onNewProductClick) {
            Text("New product")
        }
        Button(onClick = onOwnCategoriesClick) {
            Text("Own categories")
        }
        Button(onClick = onStorageLocationsClick) {
            Text("Storage locations")
        }
        Button(onClick = onFilterProduct) {
            Text("Filter product")
        }
        Button(onClick = onEditProduct) {
            Text("Edit product")
        }
        Button(onClick = onScanProduct) {
            Text("Scan product")
        }
        Button(onClick = onSettingsClick) {
            Text("Settings")
        }
    }

}