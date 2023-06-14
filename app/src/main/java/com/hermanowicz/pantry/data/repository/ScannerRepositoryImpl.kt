package com.hermanowicz.pantry.data.repository

import android.content.Context
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.di.repository.ScannerRepository
import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.ScannerMethod
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScannerRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val settingsRepository: SettingsRepository
) : ScannerRepository {

    private lateinit var scannerMethod: ScannerMethod

    override suspend fun buildScanOptions(): ScanOptions {
        val options = ScanOptions()
        val selectedCameraId: Int =
            if (settingsRepository.scanCameraMode.first() == CameraMode.REAR.name) 0 else 1
        var prompt = ""
        if (scannerMethod == ScannerMethod.ADD_BARCODE || scannerMethod == ScannerMethod.SCAN_BARCODE) {
            prompt = context.getString(R.string.scan_barcode)
            options.setDesiredBarcodeFormats(ScanOptions.PRODUCT_CODE_TYPES)
        } else {
            prompt = context.getString(R.string.scan_qr_code)
            options.setDesiredBarcodeFormats(ScanOptions.QR_CODE)
        }
        options.setPrompt(prompt)
        options.setCameraId(selectedCameraId)
        options.setOrientationLocked(true)
        options.setBeepEnabled(false)
        options.setBarcodeImageEnabled(true)
        return options
    }

    override fun decodeScanQRCodeResult(scanResult: String): Pair<Int, String> {
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

    override fun setScannerMethod(scannerMethod: ScannerMethod) {
        this.scannerMethod = scannerMethod
    }

    override fun getScannerMethod(): ScannerMethod {
        return scannerMethod
    }
}
