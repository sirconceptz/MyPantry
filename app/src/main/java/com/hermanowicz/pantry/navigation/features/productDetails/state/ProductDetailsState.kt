package com.hermanowicz.pantry.navigation.features.productDetails.state

import android.graphics.Bitmap

data class ProductDetailsState(
    val isMenuVisible: Boolean = false,
    val isDialogWarningDeleteVisible: Boolean = false,
    val onNavigateToEditProduct: Boolean = false,
    val onNavigateToAddPhoto: Boolean = false,
    val onNavigateToPrintQrCodes: Boolean = false,
    val onNavigateToMyPantry: Boolean = false,
    val goToPermissionSettings: Boolean = false,
    val photoPreview: Bitmap? = null,
    val onAddBarcode: Boolean = false
)
