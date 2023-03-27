package com.hermanowicz.mypantry.components.common.cards

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.Shapes
import timber.log.Timber

@Composable
fun GroupProductItemCard(
    groupProduct: GroupProduct,
    onClickGroupProduct: (Int) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(Shapes.medium)
            .padding(
                vertical = LocalSpacing.current.small, horizontal = LocalSpacing.current.tiny
            )
    ) {
        Column(
            modifier = Modifier
                .padding(LocalSpacing.current.small)
                .clickable {
                    Timber.d("group id: " + groupProduct.product.id.toString())
                    onClickGroupProduct(groupProduct.product.id)
                }
        ) {
            Text(text = groupProduct.product.name, fontSize = 20.sp)
            Text(
                text = stringResource(id = R.string.quantity) + ": " + groupProduct.quantity.toString(),
                fontSize = 15.sp
            )
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                if (groupProduct.product.hasSugar)
                    Text(
                        text = stringResource(R.string.sugar),
                        fontSize = 14.sp
                    )
                if (groupProduct.product.hasSalt)
                    Text(
                        text = stringResource(R.string.salt),
                        fontSize = 14.sp
                    )
                if (groupProduct.product.isVege)
                    Text(
                        text = stringResource(R.string.vege),
                        fontSize = 14.sp
                    )
                if (groupProduct.product.isBio)
                    Text(
                        text = stringResource(R.string.bio),
                        fontSize = 14.sp
                    )
            }
        }
    }
}
