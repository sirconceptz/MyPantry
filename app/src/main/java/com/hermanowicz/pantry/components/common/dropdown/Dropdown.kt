package com.hermanowicz.pantry.components.common.dropdown

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.ui.theme.Shapes
import com.hermanowicz.pantry.utils.enums.ProductDetailsOption

@Composable
fun DropdownCard(
    textLeft: String,
    mapKey: String,
    itemMap: Map<String, String>,
    onClick: () -> Unit,
    onChange: (String) -> Unit,
    visibleDropdown: Boolean,
    onDismiss: () -> Unit
) {
    val textRight = itemMap.getValue(mapKey)

    Column {
        Text(text = textLeft)
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { onClick() }
                .border(BorderStroke(1.dp, Color.Black), Shapes.medium)
        ) {
            Column(
                modifier = Modifier.padding(LocalSpacing.current.medium)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = textRight,
                        color = MaterialTheme.colors.onSurface
                    )
                    DropdownMenu(expanded = visibleDropdown, onDismissRequest = { onDismiss() }) {
                        itemMap.forEach { item ->
                            DropdownMenuItem(onClick = {
                                onChange(item.key)
                                onDismiss()
                            }) {
                                Text(text = item.value, color = MaterialTheme.colors.onSurface)
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownPrimary(
    textLeft: String,
    mapKey: String,
    itemMap: Map<String, String>,
    onClick: () -> Unit,
    onChange: (String) -> Unit,
    visibleDropdown: Boolean,
    onDismiss: () -> Unit
) {
    val textRight = itemMap.getValue(mapKey)

    Column(modifier = Modifier.clickable { onClick() }) {
        Column(
            modifier = Modifier.padding(LocalSpacing.current.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = textLeft)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = textRight
                )
                DropdownMenu(expanded = visibleDropdown, onDismissRequest = { onDismiss() }) {
                    itemMap.forEach { item ->
                        DropdownMenuItem(onClick = {
                            onChange(item.key)
                            onDismiss()
                        }) {
                            Text(text = item.value)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownPrimary(
    value: String,
    itemList: List<String>,
    onClick: () -> Unit,
    onChange: (String) -> Unit,
    visibleDropdown: Boolean,
    onDismiss: () -> Unit
) {
    Column(modifier = Modifier.clickable { onClick() }) {
        Column(
            modifier = Modifier.padding(LocalSpacing.current.medium)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = value, color = MaterialTheme.colors.onSurface)
                Spacer(modifier = Modifier.weight(1f))
                Text(
                    text = value,
                    color = MaterialTheme.colors.onSurface
                )
                DropdownMenu(expanded = visibleDropdown, onDismissRequest = { onDismiss() }) {
                    itemList.forEach { item ->
                        DropdownMenuItem(onClick = {
                            onChange(item)
                            onDismiss()
                        }) {
                            Text(text = item, color = MaterialTheme.colors.onSurface)
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownSettings(
    label: String,
    mapKey: String,
    itemMap: Map<String, String>,
    onClick: () -> Unit,
    onChange: (String) -> Unit,
    visibleDropdown: Boolean,
    onDismiss: () -> Unit,
    enabled: Boolean = true
) {
    val value = itemMap.getValue(mapKey)

    Row(
        modifier = Modifier.clickable {
            if (enabled) onClick()
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(LocalSpacing.current.medium)
        ) {
            Text(
                text = label,
                style = TextStyle(
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.onSurface
                )
            )
            Text(
                text = value,
                style = TextStyle(fontSize = 14.sp, color = MaterialTheme.colors.onSurface)
            )
            DropdownMenu(expanded = visibleDropdown, onDismissRequest = { onDismiss() }) {
                itemMap.forEach { item ->
                    DropdownMenuItem(onClick = {
                        onChange(item.key)
                        onDismiss()
                    }) {
                        Text(text = item.value, color = MaterialTheme.colors.onSurface)
                    }
                }
            }
        }
    }
}

@Composable
fun DropdownProductDetailsOptions(
    expanded: Boolean,
    onShow: (Boolean) -> Unit,
    onSelect: (String) -> Unit
) {
    DropdownMenu(expanded = expanded, onDismissRequest = { onShow(!expanded) }) {
        val itemList: List<Pair<String, String>> = ProductDetailsOption.toPairList()
        itemList.forEach { option ->
            DropdownMenuItem(onClick = {
                onSelect(option.first)
            }) {
                Text(text = option.second, color = MaterialTheme.colors.onSurface)
            }
        }
    }
}
