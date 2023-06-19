package com.hermanowicz.pantry.navigation.features.addPhoto

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.addPhoto.ui.AddPhotoScreen

@Composable
fun AddPhotoRoute(
    openDrawer: () -> Unit,
    onNavigateBack: () -> Unit
) {
    AddPhotoScreen(
        openDrawer = openDrawer,
        onNavigateBack = onNavigateBack
    )
}
