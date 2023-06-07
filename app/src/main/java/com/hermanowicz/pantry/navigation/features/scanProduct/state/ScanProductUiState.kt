package com.hermanowicz.pantry.navigation.features.scanProduct.state

import com.hermanowicz.pantry.utils.enums.ScannerMethod

data class ScanProductUiState(
    val showPutBarcodeManuallyDialog: Boolean = false,
    val barcodeManually: String = "",
    val goToPermissionSettings: Boolean = false,
    val onNavigateToProductDetails: Pair<Int, String>? = null,
    val onNavigateToNewProduct: String? = null,
    val scanType: ScannerMethod? = null
)
