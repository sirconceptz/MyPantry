package com.hermanowicz.pantry.navigation.features.scanProduct.state

data class ScanProductUiState(
    val showPutBarcodeManuallyDialog: Boolean = false,
    val barcodeManually: String = "",
    val goToPermissionSettings: Boolean = false,
    val onNavigateToProductDetails: Pair<Int, String>? = null,
    val onNavigateToNewProduct: String? = null
)