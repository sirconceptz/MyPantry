package com.hermanowicz.pantry.navigation.features.printQRCodes.state

import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.enums.QrCodeSize


data class PrintQRCodesUiState(
    var productList: List<Product> = emptyList(),
    var sizeOfQrCodeNameResId: Int = QrCodeSize.BIG.nameResId,
    var qrCodesQuantity: Int = 0,
    var onNavigateBack: Boolean = false
)