package com.hermanowicz.pantry.components.common.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.dropdown.DropdownPrimary
import com.hermanowicz.pantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.pantry.ui.theme.LocalSpacing


@Composable
fun DialogItem(
    label: String,
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onSaveClick: () -> Unit,
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
                    text = stringResource(id = R.string.save), onClick = onSaveClick
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.cancel), onClick = onDismissRequest
                )
            }
        }
    }
}

@Composable
fun DialogAuthorInfo(
    onClickFacebook: () -> Unit,
    onClickLinkedIn: () -> Unit,
    onClickAppWebsite: () -> Unit,
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
                Text(text = stringResource(id = R.string.author), fontWeight = FontWeight.Bold)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        modifier = Modifier.clickable {
                            onClickFacebook()
                        },
                        painter = painterResource(R.drawable.facebook),
                        contentDescription = null
                    )
                    Image(
                        modifier = Modifier.clickable {
                            onClickLinkedIn()
                        },
                        painter = painterResource(R.drawable.linkedin),
                        contentDescription = null
                    )
                }
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.contact_with_author),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { onClickAppWebsite() },
                    text = stringResource(id = R.string.author_app_website),
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.close), onClick = onDismissRequest
                )
            }
        }
    }
}


@Composable
fun DialogChooseNewProduct(
    label: String,
    warning: String,
    selectedProduct: String,
    dropdownVisible: Boolean,
    groupProductList: List<String>,
    onSelectGroupProduct: (String) -> Unit,
    showDropdown: (Boolean) -> Unit,
    onPositiveRequest: () -> Unit,
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
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = warning,
                    textAlign = TextAlign.Justify
                )
                DropdownPrimary(
                    value = selectedProduct,
                    itemList = groupProductList,
                    onClick = { showDropdown(true) },
                    onChange = onSelectGroupProduct,
                    visibleDropdown = dropdownVisible,
                    onDismiss = { showDropdown(false) }
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.confirm), onClick = onPositiveRequest
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.close), onClick = onDismissRequest
                )
            }
        }
    }
}

@Composable
fun DialogWarning(
    label: String,
    warning: String,
    onPositiveRequest: () -> Unit,
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
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = warning,
                    textAlign = TextAlign.Justify
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.confirm), onClick = onPositiveRequest
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.close), onClick = onDismissRequest
                )
            }
        }
    }
}


@Composable
fun DialogTextfield(
    label: String,
    value: String,
    onPositiveRequest: (String) -> Unit,
    onDismissRequest: () -> Unit
) {
    var text by remember { mutableStateOf(TextFieldValue(value)) }

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
                Text(
                    text = label,
                    fontWeight = FontWeight.Bold
                )
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = text,
                    onValueChange = { newText ->
                        text = newText
                    }
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.confirm),
                    onClick = { onPositiveRequest(text.text) }
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.close), onClick = onDismissRequest
                )
            }
        }
    }
}