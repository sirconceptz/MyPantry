package com.hermanowicz.pantry.navigation.features.scanProduct.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hermanowicz.pantry.domain.scanner.BuildScanOptionsUseCase
import com.hermanowicz.pantry.domain.scanner.DecodeQrCodeUseCase
import com.hermanowicz.pantry.domain.scanner.GetScannerMethodUseCase
import com.hermanowicz.pantry.domain.scanner.SetScannerMethodUseCase
import com.hermanowicz.pantry.utils.enums.ScannerMethod
import com.nhaarman.mockitokotlin2.*
import com.journeyapps.barcodescanner.ScanOptions
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class ScanProductViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: ScanProductViewModel
    private val mockBuildScanOptionsUseCase: BuildScanOptionsUseCase = mock()
    private val mockDecodeQrCodeUseCase: DecodeQrCodeUseCase = mock()
    private val mockSetScannerMethodUseCase: SetScannerMethodUseCase = mock()
    private val mockGetScannerMethodUseCase: GetScannerMethodUseCase = mock()

    @Before
    fun setup() {
        viewModel = ScanProductViewModel(
            mockBuildScanOptionsUseCase,
            mockDecodeQrCodeUseCase,
            mockSetScannerMethodUseCase,
            mockGetScannerMethodUseCase
        )
    }

    @Test
    fun `onPutBarcodeManually updates showPutBarcodeManuallyDialog`() {
        viewModel.onPutBarcodeManually(true)
        assertEquals(true, viewModel.uiState.value.showPutBarcodeManuallyDialog)
    }

    @Test
    fun `onNavigateToNewProduct updates onNavigateToNewProduct`() {
        viewModel.onNavigateToNewProduct("12345")
        assertEquals("12345", viewModel.uiState.value.onNavigateToNewProduct)
    }

    @Test
    fun `onNavigateToProductDetails updates onNavigateToProductDetails`() {
        val productData = Pair(1, "Product123")
        viewModel.onNavigateToProductDetails(productData)
        assertEquals(productData, viewModel.uiState.value.onNavigateToProductDetails)
    }

    @Test
    fun `onGoToPermissionSettings updates goToPermissionSettings`() {
        viewModel.onGoToPermissionSettings(true)
        assertEquals(true, viewModel.uiState.value.goToPermissionSettings)
    }

    @Test
    fun `setScanMethod updates scanner method`() {
        viewModel.setScanMethod(ScannerMethod.SCAN_QR_CODE)

        verify(mockSetScannerMethodUseCase).invoke(ScannerMethod.SCAN_QR_CODE)
    }

    @Test
    fun `getScanOptions invokes buildScanOptionsUseCase`() = runBlocking {
        val mockScanOptions: ScanOptions = mock()
        whenever(mockBuildScanOptionsUseCase()).thenReturn(mockScanOptions)

        val scanOptions = viewModel.getScanOptions()

        assertEquals(mockScanOptions, scanOptions)
    }

    @Test
    fun `onTempChangeBarcodeManuallyValue updates barcodeManually`() {
        viewModel.onTempChangeBarcodeManuallyValue("NewBarcode123")
        assertEquals("NewBarcode123", viewModel.uiState.value.barcodeManually)
    }
}
