package com.hermanowicz.pantry.navigation.features.scanProduct.state

data class ScanProductUiState(
    var showPutBarcodeManuallyDialog: Boolean = false,
    val barcodeManually: String = "",
    var goToPermissionSettings: Boolean = false,
    var onNavigateToProductDetails: Pair<Int, String>? = null,
    var onNavigateToNewProduct: String? = null
)
