package com.hermanowicz.mypantry.components.common.icons

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.hermanowicz.mypantry.R

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
