package com.hermanowicz.pantry.components.common.dialog

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonPrimary
import com.hermanowicz.pantry.components.common.dropdown.DropdownPrimary
import com.hermanowicz.pantry.components.common.textfield.TextFieldAndLabel
import com.hermanowicz.pantry.components.common.textfield.TextFieldAndLabelError
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun DialogItem(
    label: String,
    name: String,
    description: String,
    onNameChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onClearRequest: (() -> Unit)? = null,
    onSaveClick: () -> Unit,
    showError: Boolean = false,
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
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(
                        vertical = LocalSpacing.current.medium,
                        horizontal = LocalSpacing.current.small
                    ),
                verticalArrangement = Arrangement.spacedBy(LocalSpacing.current.small)
            ) {
                item {
                    Text(text = label, fontWeight = FontWeight.Bold)
                    TextFieldAndLabelError(
                        textfieldText = name,
                        labelText = stringResource(id = R.string.name),
                        textEvent = onNameChange,
                        errorText = stringResource(id = R.string.error_wrong_name),
                        placeholder = stringResource(id = R.string.name),
                        showError = showError
                    )
                    TextFieldAndLabel(
                        textfieldText = description,
                        labelText = stringResource(id = R.string.description),
                        textEvent = onDescriptionChange,
                        placeholder = stringResource(id = R.string.description)
                    )
                    if (onClearRequest != null) {
                        ButtonPrimary(
                            text = stringResource(id = R.string.clear),
                            onClick = onClearRequest
                        )
                    }
                    ButtonPrimary(
                        text = stringResource(id = R.string.save),
                        onClick = onSaveClick
                    )
                    ButtonPrimary(
                        text = stringResource(id = R.string.cancel),
                        onClick = onDismissRequest
                    )
                }
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
                    text = stringResource(id = R.string.close),
                    onClick = onDismissRequest
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
                    text = stringResource(id = R.string.confirm),
                    onClick = onPositiveRequest
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.close),
                    onClick = onDismissRequest
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
                    text = stringResource(id = R.string.confirm),
                    onClick = onPositiveRequest
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.close),
                    onClick = onDismissRequest
                )
            }
        }
    }
}

@Composable
fun DialogTextfield(
    label: String,
    value: String,
    onTempChangeValue: (String) -> Unit,
    isError: Boolean = false,
    errorStatement: String = "",
    onPositiveRequest: () -> Unit,
    onDismissRequest: () -> Unit,
    onClearRequest: (() -> Unit)? = null,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default
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
                TextFieldAndLabelError(
                    textfieldText = value,
                    labelText = label,
                    textEvent = onTempChangeValue,
                    errorText = errorStatement,
                    showError = isError,
                    keyboardOptions = keyboardOptions

                )
                if (onClearRequest != null) {
                    ButtonPrimary(
                        text = stringResource(id = R.string.clear),
                        onClick = onClearRequest
                    )
                }
                ButtonPrimary(
                    text = stringResource(id = R.string.confirm),
                    onClick = { onPositiveRequest() }
                )
                ButtonPrimary(
                    text = stringResource(id = R.string.close),
                    onClick = onDismissRequest
                )
            }
        }
    }
}
