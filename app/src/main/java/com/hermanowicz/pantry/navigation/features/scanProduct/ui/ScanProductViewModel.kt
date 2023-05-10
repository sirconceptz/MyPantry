package com.hermanowicz.pantry.navigation.features.scanProduct.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.domain.StartBarcodeScannerUseCase
import com.hermanowicz.pantry.domain.StartQrCodeCodeScannerUseCase
import com.hermanowicz.pantry.navigation.features.scanProduct.state.ScanProductUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanProductViewModel @Inject constructor(
    private val startQrCodeCodeScannerUseCase: StartQrCodeCodeScannerUseCase,
    private val startBarcodeScannerUseCase: StartBarcodeScannerUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(ScanProductUiState())
    val uiState: StateFlow<ScanProductUiState> = _uiState

    fun onScanQRCode() {
        viewModelScope.launch {
            startQrCodeCodeScannerUseCase().collect { data ->
                onNavigateToProductDetails(data)
            }
        }
    }

    fun onScanBarcode() {
        viewModelScope.launch {
            startBarcodeScannerUseCase().collect { data ->
                onNavigateToNewProduct(data)
            }
        }
    }

    fun onPutBarcodeManually(bool: Boolean) {
        _uiState.update {
            it.copy(showPutBarcodeManuallyDialog = bool)
        }
    }

    fun onNavigateToNewProduct(barcodeData: String?) {
        _uiState.update { it.copy(onNavigateToNewProduct = barcodeData) }
    }

    fun onNavigateToProductDetails(productData: Pair<Int, String>?) {
        _uiState.update { it.copy(onNavigateToProductDetails = productData) }
    }

    fun onGoToPermissionSettings(bool: Boolean) {
        _uiState.update {
            it.copy(goToPermissionSettings = bool)
        }
    }
}
