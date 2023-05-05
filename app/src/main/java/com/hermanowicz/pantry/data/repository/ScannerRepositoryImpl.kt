package com.hermanowicz.pantry.data.repository

import android.content.Context
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.hermanowicz.pantry.di.repository.ScannerRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScannerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context
) : ScannerRepository {
    override fun startQRCodeScanning(): Flow<Pair<Int, String>> {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_QR_CODE)
            .build()
        val scanner = GmsBarcodeScanning.getClient(context, options)
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        send(getQrCodeDetails(it))
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }
    }

    override fun startBarcodeScanning(): Flow<String> {
        val options = GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_UPC_A, Barcode.FORMAT_UPC_E)
            .build()
        val scanner = GmsBarcodeScanning.getClient(context, options)
        return callbackFlow {
            scanner.startScan()
                .addOnSuccessListener {
                    launch {
                        send(it.rawValue ?: "")
                    }
                }.addOnFailureListener {
                    it.printStackTrace()
                }
            awaitClose { }
        }
    }

    private fun getQrCodeDetails(barcode: Barcode): Pair<Int, String> {
        return when (barcode.valueType) {
            Barcode.TYPE_PRODUCT -> {
                decodeScanQRCodeResult(barcode.displayValue ?: "")
            }

            Barcode.TYPE_TEXT -> {
                decodeScanQRCodeResult(barcode.rawValue ?: "")
            }

            Barcode.TYPE_UNKNOWN -> {
                decodeScanQRCodeResult(barcode.rawValue ?: "")
            }

            else -> {
                decodeScanQRCodeResult("")
            }
        }
    }

    private fun decodeScanQRCodeResult(scanResult: String): Pair<Int, String> {
        var decodedResult = Pair(0, "")
        try {
            val jsonObject = JSONObject(scanResult)
            val id = jsonObject.getInt("product_id")
            val hashcode = jsonObject.getInt("hash_code")
            decodedResult = Pair(id, hashcode.toString())
        } catch (e: JSONException) {
            Timber.tag("Decode scan result error").e(e.toString())
        }
        return decodedResult
    }
}
