package com.hermanowicz.mypantry.components.common.textfield

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hermanowicz.mypantry.ui.theme.Shapes

@Composable
fun TextFieldAndLabel(
    textfieldText: String,
    labelText: String,
    textEvent: (String) -> Unit,
    placeholder: String,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    Column() {
        Text(text = labelText)
        TextField(
            value = textfieldText,
            onValueChange = {
                textEvent(it)
            },
            modifier = Modifier
                .background(
                    color = Color.White,
                    shape = Shapes.medium,
                )
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.Black,
                    shape = Shapes.medium,
                ),
            placeholder = { Text(text = placeholder) },
            shape = Shapes.medium,
            maxLines = maxLines,
            singleLine = singleLine,
            colors = TextFieldDefaults.textFieldColors(
                backgroundColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                disabledIndicatorColor = Color.Transparent
            ),
            textStyle = TextStyle(
                fontSize = 15.sp
            ),
            keyboardOptions = keyboardOptions,
            keyboardActions = keyboardActions,
            visualTransformation = visualTransformation
        )
    }
}

@Composable
fun TextFieldAndLabelDate(
    textfieldText: String,
    labelText: String,
    onClickTextfield: () -> Unit,
    visualTransformation: VisualTransformation = VisualTransformation.None,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    keyboardActions: KeyboardActions = KeyboardActions(),
    singleLine: Boolean = true,
    maxLines: Int = 1,
) {
    Column() {
        Text(text = labelText)
        Box(modifier = Modifier.clickable { onClickTextfield() }) {
            TextField(
                value = textfieldText,
                onValueChange = {
                },
                enabled = false,
                modifier = Modifier
                    .background(
                        color = Color.White,
                        shape = Shapes.medium,
                    )
                    .fillMaxWidth()
                    .border(
                        width = 1.dp,
                        color = Color.Black,
                        shape = Shapes.medium,
                    ),
                shape = Shapes.medium,
                maxLines = maxLines,
                singleLine = singleLine,
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledIndicatorColor = Color.Transparent
                ),
                textStyle = TextStyle(
                    fontSize = 15.sp
                ),
                keyboardOptions = keyboardOptions,
                keyboardActions = keyboardActions,
                visualTransformation = visualTransformation
            )
        }
    }
}

@Preview
@Composable
private fun Preview() {
    TextFieldAndLabel(
        textfieldText = "Test",
        labelText = "Test",
        textEvent = {},
        placeholder = "Placeholder"
    )
}
