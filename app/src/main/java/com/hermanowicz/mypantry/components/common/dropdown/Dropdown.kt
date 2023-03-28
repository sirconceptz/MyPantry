package com.hermanowicz.mypantry.components.common.dropdown

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DropdownMenu
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun DropdownMainUnitInsideCard(
    textLeft: String,
    textRight: String,
    onClick: () -> Unit,
    onChange: (String) -> Unit,
    visibleDropdown: Boolean,
    onDismiss: () -> Unit
) {
    val context = LocalContext.current
    Box(modifier = Modifier.clickable { onClick() }) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = textLeft,
            )
            Spacer(Modifier.weight(1f))
            Text(
                text = textRight,
                modifier = Modifier.padding(end = LocalSpacing.current.small)
            )
            DropdownMenu(expanded = visibleDropdown, onDismissRequest = { onDismiss() }) {
            }
        }
    }
}
