package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.di.repository.ScannerRepository
import com.hermanowicz.pantry.di.repository.SettingsRepository
import com.hermanowicz.pantry.utils.enums.CameraMode
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.flow.first
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ScannerRepositoryImpl @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ScannerRepository {

    override suspend fun buildScanOptions(): ScanOptions {
        val selectedCameraId: Int =
            if (settingsRepository.scanCameraMode.first() == CameraMode.REAR.name) 0 else 1
        val prompt = "Scan code"
        val options = ScanOptions()
        options.setDesiredBarcodeFormats(ScanOptions.ALL_CODE_TYPES)
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
}
