package com.hermanowicz.mypantry.components.common.dialog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.window.Dialog
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.button.ButtonPrimary
import com.hermanowicz.mypantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun DialogAddNewItem(
    label: String,
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onAddClick: () -> Unit,
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.small)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = LocalSpacing.current.medium,
                        horizontal = LocalSpacing.current.small
                    ),
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
            ) {
                Text(text = label, fontWeight = FontWeight.Bold)
                TextFieldAndLabel(
                    textfieldText = name,
                    labelText = stringResource(id = R.string.name),
                    textEvent = onNameChange,
                    placeholder = stringResource(id = R.string.name)
                )
                TextFieldAndLabel(
                    textfieldText = description,
                    labelText = stringResource(id = R.string.description),
                    textEvent = onDescriptionChange,
                    placeholder = stringResource(id = R.string.description)
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.save), onClick = onAddClick
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.cancel), onClick = onDismissRequest
                )
            }
        }
    }
}
