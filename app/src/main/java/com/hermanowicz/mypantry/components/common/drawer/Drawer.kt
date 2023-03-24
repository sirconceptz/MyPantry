package com.hermanowicz.mypantry.components.common.drawer

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.ui.theme.GradientCenter
import com.hermanowicz.mypantry.ui.theme.GradientStart
import com.hermanowicz.mypantry.ui.theme.GradientStop
import com.hermanowicz.mypantry.ui.theme.LocalSpacing

@Composable
fun AppDrawerView(
    onMyPantry: () -> Unit,
    onNewProduct: () -> Unit,
    onOwnCategories: () -> Unit,
    onStorageLocations: () -> Unit,
    onSettings: () -> Unit,
    onScanProduct: () -> Unit
) {
    LazyColumn() {
        item {
            DrawerHeader()
        }
        item {
            DrawerMenuItem(
                iconDrawableId = R.drawable.ic_my_pantry_shortcut,
                text = stringResource(id = R.string.my_pantry),
                onItemClick = onMyPantry
            )
        }
        item {
            DrawerMenuItem(
                iconDrawableId = R.drawable.ic_add_item,
                text = stringResource(id = R.string.new_product),
                onItemClick = onNewProduct
            )
        }
        item {
            DrawerMenuItem(
                iconDrawableId = R.drawable.ic_scan_qr_code,
                text = stringResource(id = R.string.scan_product),
                onItemClick = onScanProduct
            )
        }
        item {
            DrawerMenuItem(
                iconDrawableId = R.drawable.ic_categories_storages,
                text = stringResource(id = R.string.own_categories),
                onItemClick = onOwnCategories
            )
        }
        item {
            DrawerMenuItem(
                iconDrawableId = R.drawable.ic_categories_storages,
                text = stringResource(id = R.string.storage_locations),
                onItemClick = onStorageLocations
            )
        }
        item {
            DrawerMenuItem(
                iconDrawableId = R.drawable.ic_app_settings,
                text = stringResource(id = R.string.settings),
                onItemClick = onSettings
            )
        }
    }
}

@Composable
private fun DrawerHeader() {
    Box(
        modifier = Modifier
            .height(140.dp)
            .fillMaxWidth()
            .background(
                brush = Brush.horizontalGradient(
                    colors = listOf(
                        GradientStart,
                        GradientCenter,
                        GradientStop
                    )
                )
            )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(LocalSpacing.current.medium),
            verticalArrangement = Arrangement.Bottom,
        ) {
            Text(
                text = stringResource(id = R.string.app_name),
                style = TextStyle(
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White
                )
            )
            Text(
                text = stringResource(id = R.string.what_do_you_want_to_do),
                style = TextStyle(
                    fontSize = 15.sp,
                    color = Color.White
                )
            )
        }
    }
}

@Composable
private fun DrawerMenuItem(
    iconDrawableId: Int,
    text: String,
    onItemClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onItemClick() }
            .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Icon(
            painter = painterResource(iconDrawableId),
            contentDescription = null,
        )
        Spacer(modifier = Modifier.width(LocalSpacing.current.medium))
        Text(text = text)
    }
}
