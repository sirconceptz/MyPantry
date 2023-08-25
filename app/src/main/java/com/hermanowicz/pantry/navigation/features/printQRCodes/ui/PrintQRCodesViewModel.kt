package com.hermanowicz.pantry.navigation.features.printQRCodes.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.domain.pdf.CreatePdfDocumentUseCase
import com.hermanowicz.pantry.domain.product.GetProductListByIdsProductsUseCase
import com.hermanowicz.pantry.domain.settings.FetchQrCodeSizeUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.navigation.features.printQRCodes.state.PrintQRCodesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class PrintQRCodesViewModel @Inject constructor(
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase,
    private val getProductListByIdsProductsUseCase: GetProductListByIdsProductsUseCase,
    private val fetchQrCodeSizeUseCase: FetchQrCodeSizeUseCase,
    private val createPdfDocumentUseCase: CreatePdfDocumentUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrintQRCodesUiState())
    var uiState: StateFlow<PrintQRCodesUiState> = _uiState.asStateFlow()

    init {
        val savedProductIdList = savedStateHandle["productIdList"] ?: "0"
        val productIdArray = savedProductIdList.split(";")
        val productIdList = productIdArray.map { it.toInt() }
        fetchProducts(productIdList)
    }

    fun fetchProducts(productIdList: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                combine(
                    observeDatabaseModeUseCase(),
                    fetchQrCodeSizeUseCase()
                ) { databaseMode, qrCodeSize ->
                    getProductListByIdsProductsUseCase(
                        databaseMode,
                        productIdList
                    ).collect { products ->
                        _uiState.update {
                            it.copy(
                                productList = products,
                                qrCodesQuantity = products.size,
                                sizeOfQrCodeNameResId = qrCodeSize.nameResId
                            )
                        }
                    }
                }.collect()
            } catch (e: Exception) {
                Timber.e(e.message)
            }
        }
    }

    fun onPrintCodesPermissionGranted() {
        viewModelScope.launch(Dispatchers.IO) {
            val fileName = createPdfDocumentUseCase(uiState.value.productList)
            _uiState.update {
                it.copy(
                    pdfFileName = fileName,
                    navigateToPrintQrCodes = true
                )
            }
        }
    }

    fun onSharePdfDocumentPermissionGranted() {
        viewModelScope.launch(Dispatchers.IO) {
            val fileName = createPdfDocumentUseCase(uiState.value.productList)
            _uiState.update {
                it.copy(
                    pdfFileName = fileName,
                    navigateToSharePdfDocument = true
                )
            }
        }
    }

    fun clearPdfState() {
        _uiState.update {
            it.copy(
                pdfFileName = null,
                navigateToSharePdfDocument = false,
                navigateToPrintQrCodes = false
            )
        }
    }

    fun onGoToPermissionSettings(bool: Boolean) {
        _uiState.update {
            it.copy(goToPermissionSettings = bool)
        }
    }
}
