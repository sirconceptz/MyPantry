package com.hermanowicz.pantry.components.common.image

import android.graphics.Bitmap
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import com.hermanowicz.pantry.ui.theme.LocalSpacing

@Composable
fun PhotoBox(bitmap: Bitmap) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = LocalSpacing.current.medium,
                bottom = LocalSpacing.current.small
            )
    ) {
        Image(
            modifier = Modifier
                .fillMaxHeight(0.3f)
                .align(Alignment.Center),
            bitmap = bitmap.asImageBitmap(),
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}
