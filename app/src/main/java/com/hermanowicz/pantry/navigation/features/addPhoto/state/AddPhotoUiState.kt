package com.hermanowicz.pantry.navigation.features.addPhoto.state

import android.graphics.Bitmap
import com.hermanowicz.pantry.data.model.Product

data class AddPhotoUiState(
    val onNavigateBack: Boolean = false,
    val onClickAddPhoto: Boolean = false,
    val goToPermissionSettings: Boolean = false,
    val productList: List<Product> = emptyList(),
    val photoPreview: Bitmap? = null
)
