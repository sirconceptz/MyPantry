package com.hermanowicz.mypantry.components.common.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.button.ButtonTransparent
import com.hermanowicz.mypantry.components.common.checkbox.CircleCheckbox
import com.hermanowicz.mypantry.components.common.divider.DividerCardInside
import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.data.model.StorageLocation
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.Shapes
import com.hermanowicz.mypantry.utils.ProductDataState

@Composable
fun GroupProductItemCard(
    groupProduct: GroupProduct,
    onClickGroupProduct: (Int) -> Unit
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
                    onClickGroupProduct(groupProduct.product.id)
                }
        ) {
            Text(text = groupProduct.product.name, fontSize = 20.sp)
            Text(
                text = stringResource(id = R.string.quantity) + ": " + groupProduct.quantity.toString(),
                fontSize = 15.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End
            ) {
                if (groupProduct.product.hasSugar) Text(
                    text = stringResource(R.string.sugar), fontSize = 14.sp
                )
                if (groupProduct.product.hasSalt) Text(
                    text = stringResource(R.string.salt), fontSize = 14.sp
                )
                if (groupProduct.product.isVege) Text(
                    text = stringResource(R.string.vege), fontSize = 14.sp
                )
                if (groupProduct.product.isBio) Text(
                    text = stringResource(R.string.bio), fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
fun StorageLocationItemCard(
    storageLocation: StorageLocation,
    isEditMode: Boolean,
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
                fontWeight = FontWeight.Bold
            )
            Text(
                modifier = Modifier.fillMaxWidth(),
                text = storageLocation.description,
                fontSize = 15.sp,
                textAlign = TextAlign.Justify
            )
            if (isEditMode) {
                Spacer(modifier = Modifier.height(30.dp))
                ButtonTransparent(text = stringResource(id = R.string.delete)) {
                    onClickDeleteStorageLocation(storageLocation)
                }
            }
        }
    }
}

@Composable
fun ProductDetailsAttributesCard(
    productDataState: ProductDataState,
    onIsVegeChange: (Boolean) -> Unit,
    onIsBioChange: (Boolean) -> Unit,
    onHasSugarChange: (Boolean) -> Unit,
    onHasSaltChange: (Boolean) -> Unit
) {
    Column() {
        Text(text = stringResource(id = R.string.product_attributes))
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(Shapes.medium)
                .padding(horizontal = LocalSpacing.current.tiny)
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
                        selected = productDataState.isVege,
                        onChecked = { onIsVegeChange(!productDataState.isVege) }
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
                        selected = productDataState.isBio,
                        onChecked = { onIsBioChange(!productDataState.isBio) }
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
                        selected = productDataState.hasSugar,
                        onChecked = { onHasSugarChange(!productDataState.isVege) }
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
                        selected = productDataState.hasSalt,
                        onChecked = { onHasSaltChange(!productDataState.hasSalt) }
                    )
                }
            }
        }
    }
}
