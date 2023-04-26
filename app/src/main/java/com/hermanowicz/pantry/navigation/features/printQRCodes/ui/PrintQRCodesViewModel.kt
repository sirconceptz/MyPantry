package com.hermanowicz.pantry.navigation.features.printQRCodes.ui

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.domain.FetchDatabaseModeUseCase
import com.hermanowicz.pantry.domain.FetchQrCodeSizeUseCase
import com.hermanowicz.pantry.domain.GetProductListByIdsProductsUseCase
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
import javax.inject.Inject

@HiltViewModel
class PrintQRCodesViewModel @Inject constructor(
    private val fetchDatabaseModeUseCase: FetchDatabaseModeUseCase,
    private val getProductListByIdsProductsUseCase: GetProductListByIdsProductsUseCase,
    private val fetchQrCodeSizeUseCase: FetchQrCodeSizeUseCase,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(PrintQRCodesUiState())
    var uiState: StateFlow<PrintQRCodesUiState> = _uiState.asStateFlow()

    init {
        val savedProductIdList = savedStateHandle["productIdList"] ?: ""
        val productIdArray = savedProductIdList.split(";")
        val productIdList = productIdArray.map { it.toInt() }
        fetchProducts(productIdList)
    }

    private fun fetchProducts(productIdList: List<Int>) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                combine(
                    fetchDatabaseModeUseCase(),
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
                _uiState.update {
                    it.copy(
                        productList = emptyList(),
                        qrCodesQuantity = 0,
                        onNavigateBack = true
                    )
                }
            }
        }
    }

    fun onClickPrint() {

    }


    fun onClickSharePdfDocument() {

    }

    fun onNavigateBack(bool: Boolean) {
        _uiState.update {
            it.copy(onNavigateBack = bool)
        }
    }

}