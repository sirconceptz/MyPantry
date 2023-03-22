package com.hermanowicz.mypantry.navigation.features.newProduct.ui

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.components.common.button.ButtonPrimary
import com.hermanowicz.mypantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun NewProductScreen(
    viewModel: NewProductViewModel = hiltViewModel()
) {
    val productDataState by viewModel.productDataState.collectAsState()

    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = LocalSpacing.current.small)
    ) {
        item {
            TextFieldAndLabel(
                textfieldText = productDataState.name,
                labelText = "Name",
                textEvent = { viewModel.onNameChange(it) },
                placeholder = "Name"
            )
        }
        item {
            TextFieldAndLabel(
                textfieldText = productDataState.dosage,
                labelText = "Dosage",
                textEvent = { viewModel.onDosageChange(it) },
                placeholder = "Dosage"
            )
        }
        item {
            ButtonPrimary(text = "Save product", onClick = { viewModel.onSaveClick() })
        }
    }
}
