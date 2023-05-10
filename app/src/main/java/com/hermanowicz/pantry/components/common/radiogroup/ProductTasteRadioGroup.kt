package com.hermanowicz.pantry.components.common.radiogroup

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.RadioButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonTransparent
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.ui.theme.Shapes

@Composable
fun ProductTasteRadioGroup(
    itemList: List<Pair<String, String>>,
    selectedItem: String,
    onSelect: (String) -> Unit,
    onCleanAll: () -> Unit
) {
    Column() {
        Text(text = stringResource(id = R.string.taste))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.tiny),
            border = BorderStroke(width = LocalSpacing.current.line, color = Color.Black),
            shape = Shapes.medium
        ) {
            Column(
                modifier = Modifier.padding(
                    vertical = LocalSpacing.current.small,
                    horizontal = LocalSpacing.current.medium
                ),
                verticalArrangement = Arrangement.Center
            ) {
                itemList.forEach { item ->
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(text = item.second)
                        RadioButton(
                            selected = selectedItem == item.first,
                            onClick = { onSelect(item.first) })
                    }
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    ButtonTransparent(
                        text = stringResource(id = R.string.clean),
                        onClick = onCleanAll
                    )
                }
            }
        }
    }
}