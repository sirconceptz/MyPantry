package com.hermanowicz.pantry.navigation.features.scanProduct.state

import com.hermanowicz.pantry.utils.enums.ScannerMethod

data class ScanProductUiState(
    var showPutBarcodeManuallyDialog: Boolean = false,
    val barcodeManually: String = "",
    var goToPermissionSettings: Boolean = false,
    var onNavigateToProductDetails: Pair<Int, String>? = null,
    var onNavigateToNewProduct: String? = null,
    val scanType: ScannerMethod? = null
)
