package com.hermanowicz.pantry.data.repository

import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.hermanowicz.pantry.di.repository.ScannerRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScannerRepositoryImpl @Inject constructor(
    private val scanner: GmsBarcodeScanner,
) : ScannerRepository {
    override fun startScanning(): Flow<String?> {
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        send(getDetails(it))
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }
    }

    private fun getDetails(barcode: Barcode): String {
        return when (barcode.valueType) {
            Barcode.TYPE_PRODUCT -> {
                "productType : ${barcode.displayValue}"
            }
            Barcode.TYPE_TEXT -> {
                "text : ${barcode.rawValue}"
            }
            Barcode.TYPE_UNKNOWN -> {
                "unknown : ${barcode.rawValue}"
            }
            else -> {
                ""
            }
        }
    }
}
