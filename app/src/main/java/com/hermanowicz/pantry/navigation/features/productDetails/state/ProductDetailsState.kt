package com.hermanowicz.pantry.navigation.features.productDetails.state

data class ProductDetailsState(
    val isMenuVisible: Boolean = false,
    val isDialogWarningDeleteVisible: Boolean = false,
    val onNavigateToEditProduct: Boolean = false,
    val onNavigateToPrintQrCodes: Boolean = false,
    val onNavigateToMyPantry: Boolean = false,
    val onAddBarcode: Boolean = false
)