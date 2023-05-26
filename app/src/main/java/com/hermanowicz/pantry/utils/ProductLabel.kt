package com.hermanowicz.pantry.utils

import android.graphics.Bitmap

data class ProductLabel(
    val bitmap: Bitmap?,
    val productName: String?,
    val expirationDate: String?,
    val productionDate: String?,
    val qrCodeContent: String?
)
