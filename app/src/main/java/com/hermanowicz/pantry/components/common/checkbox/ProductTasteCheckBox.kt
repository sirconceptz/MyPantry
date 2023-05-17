package com.hermanowicz.pantry.components.common.checkbox

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.navigation.features.filterProduct.state.FilterProductDataState
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.ui.theme.Shapes
import com.hermanowicz.pantry.utils.enums.Taste

@Composable
fun ProductTasteCheckboxes(
    filterProductDataState: FilterProductDataState,
    onSelect: (selected: String, bool: Boolean) -> Unit
) {
    Column {
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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = Taste.SWEET.nameResId))
                    CircleCheckbox(
                        selected = filterProductDataState.sweet,
                        onChecked = { onSelect(Taste.SWEET.name, !filterProductDataState.sweet) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = Taste.SOUR.nameResId))
                    CircleCheckbox(
                        selected = filterProductDataState.sour,
                        onChecked = { onSelect(Taste.SOUR.name, !filterProductDataState.sour) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = Taste.SWEET_AND_SOUR.nameResId))
                    CircleCheckbox(
                        selected = filterProductDataState.sweetAndSour,
                        onChecked = {
                            onSelect(
                                Taste.SWEET_AND_SOUR.name,
                                !filterProductDataState.sweetAndSour
                            )
                        })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = Taste.SALTY.nameResId))
                    CircleCheckbox(
                        selected = filterProductDataState.salty,
                        onChecked = { onSelect(Taste.SALTY.name, !filterProductDataState.salty) })
                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(id = Taste.SPICY.nameResId))
                    CircleCheckbox(
                        selected = filterProductDataState.spicy,
                        onChecked = { onSelect(Taste.SPICY.name, !filterProductDataState.spicy) })
                }
            }
        }
    }
}