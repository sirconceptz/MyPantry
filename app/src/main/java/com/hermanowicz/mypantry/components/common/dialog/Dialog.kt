package com.hermanowicz.mypantry.components.common.dialog

import androidx.compose.material.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.window.Dialog

@Composable
fun DialogPrimary(
    onDismissRequest: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card() {
        }
    }
}
