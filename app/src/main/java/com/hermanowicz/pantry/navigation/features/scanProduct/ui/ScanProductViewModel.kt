package com.hermanowicz.pantry.navigation.features.scanProduct.ui

import androidx.lifecycle.ViewModel
import com.hermanowicz.pantry.domain.scanner.BuildScanOptionsUseCase
import com.hermanowicz.pantry.domain.scanner.DecodeQrCodeUseCase
import com.hermanowicz.pantry.domain.scanner.GetScannerMethodUseCase
import com.hermanowicz.pantry.domain.scanner.SetScannerMethodUseCase
import com.hermanowicz.pantry.navigation.features.scanProduct.state.ScanProductUiState
import com.hermanowicz.pantry.utils.enums.ScannerMethod
import com.journeyapps.barcodescanner.ScanIntentResult
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@HiltViewModel
class ScanProductViewModel @Inject constructor(
    private val buildScanOptionsUseCase: BuildScanOptionsUseCase,
    private val decodeQrCodeUseCase: DecodeQrCodeUseCase,
    private val setScannerMethodUseCase: SetScannerMethodUseCase,
    private val getScannerMethodUseCase: GetScannerMethodUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScanProductUiState())
    val uiState: StateFlow<ScanProductUiState> = _uiState

    fun onPutBarcodeManually(bool: Boolean) {
        _uiState.update {
            it.copy(showPutBarcodeManuallyDialog = bool)
        }
    }

    fun onPutBarcodeManually() {
        val barcode = uiState.value.barcodeManually
        onNavigateToNewProduct(barcode)
    }

    fun onNavigateToNewProduct(barcodeData: String) {
        if (barcodeData.isNotEmpty()) {
            _uiState.update { it.copy(onNavigateToNewProduct = barcodeData) }
        }
    }

    fun onNavigateToProductDetails(productData: Pair<Int, String>?) {
        _uiState.update { it.copy(onNavigateToProductDetails = productData) }
    }

    fun onGoToPermissionSettings(bool: Boolean) {
        _uiState.update {
            it.copy(goToPermissionSettings = bool)
        }
    }

    fun setScanResult(result: ScanIntentResult) {
        val scannerMethod = getScannerMethodUseCase()
        if (result.contents != null) {
            if (scannerMethod == ScannerMethod.SCAN_BARCODE) {
                onNavigateToNewProduct(result.contents)
            } else if (scannerMethod == ScannerMethod.SCAN_QR_CODE) {
                onNavigateToProductDetails(decodeQrCodeUseCase(result.contents))
            }
        }
    }

    fun setScanMethod(scannerMethod: ScannerMethod) {
        setScannerMethodUseCase(scannerMethod)
    }

    fun getScanOptions(): ScanOptions = runBlocking { buildScanOptionsUseCase() }

    fun onTempChangeBarcodeManuallyValue(tempBarcodeManually: String) {
        _uiState.update {
            it.copy(barcodeManually = tempBarcodeManually)
        }
    }
}
