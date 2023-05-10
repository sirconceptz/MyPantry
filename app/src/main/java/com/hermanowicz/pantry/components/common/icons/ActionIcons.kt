package com.hermanowicz.pantry.components.common.icons

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hermanowicz.pantry.R

@Composable
fun EditIcon(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, content = {
        Icon(
            painter = painterResource(id = R.drawable.ic_edit),
            contentDescription = null,
            tint = Color.White
        )
    })
}

@Composable
fun PrintIcon(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, content = {
        Icon(
            painter = painterResource(id = R.drawable.ic_print),
            contentDescription = null,
            tint = Color.White
        )
    })
}

@Composable
fun MenuIcon(
    onClick: () -> Unit
) {
    IconButton(onClick = onClick, content = {
        Icon(
            painter = painterResource(id = R.drawable.ic_menu),
            contentDescription = null,
            tint = Color.White
        )
    })
}
