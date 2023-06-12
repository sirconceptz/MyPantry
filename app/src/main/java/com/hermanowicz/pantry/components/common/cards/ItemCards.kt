package com.hermanowicz.pantry.components.common.cards

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.button.ButtonTransparent
import com.hermanowicz.pantry.components.common.checkbox.CircleCheckbox
import com.hermanowicz.pantry.components.common.divider.DividerCardInside
import com.hermanowicz.pantry.components.common.dropdown.DropdownPrimary
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.ui.theme.Shapes
import com.hermanowicz.pantry.utils.DateAndTimeConverter
import com.hermanowicz.pantry.utils.enums.ProductAttributesValueType

@Composable
fun GroupProductItemCard(
    groupProduct: GroupProduct,
    onClickGroupProduct: (Pair<Int, String>) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .padding(vertical = LocalSpacing.current.small, horizontal = LocalSpacing.current.tiny)
    ) {
        Column(
            modifier = Modifier
                .padding(LocalSpacing.current.small)
                .clickable {
                    onClickGroupProduct(
                        Pair(
                            groupProduct.product.id,
                            groupProduct.product.hashCode
                        )
                    )
                }
        ) {
            Text(text = groupProduct.product.name, fontSize = 20.sp)
            Text(
                text = stringResource(id = R.string.expiration_date) + ": " + DateAndTimeConverter.dateToVisibleWithYear(
                    groupProduct.product.expirationDate
                ),
                fontSize = 15.sp
            )
            Text(
                text = stringResource(id = R.string.quantity) + ": " + groupProduct.quantity.toString(),
                fontSize = 15.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(
                    space = 20.dp,
                    alignment = Alignment.End
                )
            ) {
                Text(
                    text = stringResource(R.string.sugar),
                    fontSize = 14.sp,
                    color = if (groupProduct.product.hasSugar) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                )
                Text(
                    text = stringResource(R.string.salt),
                    fontSize = 14.sp,
                    color = if (groupProduct.product.hasSalt) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                )
                Text(
                    text = stringResource(R.string.vege),
                    fontSize = 14.sp,
                    color = if (groupProduct.product.isVege) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                )
                Text(
                    text = stringResource(R.string.bio),
                    fontSize = 14.sp,
                    color = if (groupProduct.product.isBio) MaterialTheme.colors.onSurface else MaterialTheme.colors.surface
                )
            }
        }
    }
}

@Composable
fun StorageLocationItemCard(
    storageLocation: StorageLocation,
    isEditMode: Boolean,
    onClickEditStorageLocation: (StorageLocation) -> Unit,
    onClickDeleteStorageLocation: (StorageLocation) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .padding(vertical = LocalSpacing.current.small, horizontal = LocalSpacing.current.tiny)
    ) {
        Column(
            modifier = Modifier
                .padding(LocalSpacing.current.small)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = storageLocation.name,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = storageLocation.description,
                fontSize = 15.sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colors.onSurface
            )
            if (isEditMode) {
                Spacer(modifier = Modifier.height(30.dp))
                ButtonTransparent(text = stringResource(id = R.string.edit)) {
                    onClickEditStorageLocation(storageLocation)
                }
                ButtonTransparent(text = stringResource(id = R.string.delete)) {
                    onClickDeleteStorageLocation(storageLocation)
                }
            }
        }
    }
}

@Composable
fun CategoryItemCard(
    category: Category,
    isEditMode: Boolean,
    onClickEditCategory: (Category) -> Unit,
    onClickDeleteCategory: (Category) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .padding(vertical = LocalSpacing.current.small, horizontal = LocalSpacing.current.tiny)
    ) {
        Column(
            modifier = Modifier
                .padding(LocalSpacing.current.small)
        ) {
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = category.name,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.onSurface
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = category.description,
                fontSize = 15.sp,
                textAlign = TextAlign.Justify,
                color = MaterialTheme.colors.onSurface
            )
            if (isEditMode) {
                Spacer(modifier = Modifier.height(30.dp))
                ButtonTransparent(text = stringResource(id = R.string.edit)) {
                    onClickEditCategory(category)
                }
                ButtonTransparent(text = stringResource(id = R.string.delete)) {
                    onClickDeleteCategory(category)
                }
            }
        }
    }
}

@Composable
fun ProductDetailsAttributesCard(
    isVege: Boolean,
    isBio: Boolean,
    hasSugar: Boolean,
    hasSalt: Boolean,
    onIsVegeChange: (Boolean) -> Unit,
    onIsBioChange: (Boolean) -> Unit,
    onHasSugarChange: (Boolean) -> Unit,
    onHasSaltChange: (Boolean) -> Unit
) {
    Column {
        Text(text = stringResource(id = R.string.product_attributes))
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
                    Text(text = stringResource(R.string.vege))
                    CircleCheckbox(
                        selected = isVege,
                        onChecked = { onIsVegeChange(!isVege) }
                    )
                }
                DividerCardInside()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.bio))
                    CircleCheckbox(
                        selected = isBio,
                        onChecked = { onIsBioChange(!isBio) }
                    )
                }
                DividerCardInside()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.sugar))
                    CircleCheckbox(
                        selected = hasSugar,
                        onChecked = { onHasSugarChange(!hasSugar) }
                    )
                }
                DividerCardInside()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = stringResource(R.string.salt))
                    CircleCheckbox(
                        selected = hasSalt,
                        onChecked = { onHasSaltChange(!hasSalt) }
                    )
                }
            }
        }
    }
}

@Composable
fun FilterProductDetailsAttributesCard(
    isVege: String,
    isBio: String,
    hasSugar: String,
    hasSalt: String,
    onIsVegeChange: (String) -> Unit,
    onIsBioChange: (String) -> Unit,
    onHasSugarChange: (String) -> Unit,
    onHasSaltChange: (String) -> Unit,
    showIsVegeDropdown: (Boolean) -> Unit,
    showIsBioDropdown: (Boolean) -> Unit,
    showHasSugarDropdown: (Boolean) -> Unit,
    showHasSaltDropdown: (Boolean) -> Unit,
    isVegeDropdownVisible: Boolean,
    isBioDropdownVisible: Boolean,
    hasSugarDropdownVisible: Boolean,
    hasSaltDropdownVisible: Boolean
) {
    val map: Map<String, String> = ProductAttributesValueType.toMap()
    Column {
        Text(text = stringResource(id = R.string.product_attributes))
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
                    DropdownPrimary(
                        textLeft = stringResource(id = R.string.vege),
                        mapKey = isVege,
                        itemMap = map,
                        onClick = { showIsVegeDropdown(true) },
                        onChange = onIsVegeChange,
                        visibleDropdown = isVegeDropdownVisible,
                        onDismiss = { showIsVegeDropdown(false) }
                    )
                }
                DividerCardInside()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownPrimary(
                        textLeft = stringResource(id = R.string.bio),
                        mapKey = isBio,
                        itemMap = map,
                        onClick = { showIsBioDropdown(true) },
                        onChange = onIsBioChange,
                        visibleDropdown = isBioDropdownVisible,
                        onDismiss = { showIsBioDropdown(false) }
                    )
                }
                DividerCardInside()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownPrimary(
                        textLeft = stringResource(id = R.string.sugar),
                        mapKey = hasSugar,
                        itemMap = map,
                        onClick = { showHasSugarDropdown(true) },
                        onChange = onHasSugarChange,
                        visibleDropdown = hasSugarDropdownVisible,
                        onDismiss = { showHasSugarDropdown(false) }
                    )
                }
                DividerCardInside()
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    DropdownPrimary(
                        textLeft = stringResource(id = R.string.salt),
                        mapKey = hasSalt,
                        itemMap = map,
                        onClick = { showHasSaltDropdown(true) },
                        onChange = onHasSaltChange,
                        visibleDropdown = hasSaltDropdownVisible,
                        onDismiss = { showHasSaltDropdown(false) }
                    )
                }
            }
        }
    }
}
