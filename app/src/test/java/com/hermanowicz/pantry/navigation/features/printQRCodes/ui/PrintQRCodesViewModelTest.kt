package com.hermanowicz.pantry.navigation.features.printQRCodes.ui

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.SavedStateHandle
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.pdf.CreatePdfDocumentUseCase
import com.hermanowicz.pantry.domain.product.GetProductListByIdsProductsUseCase
import com.hermanowicz.pantry.domain.settings.FetchQrCodeSizeUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class PrintQRCodesViewModelTest {

    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase = mockk()

    private val getProductListByIdsProductsUseCase: GetProductListByIdsProductsUseCase = mockk()

    private val fetchQrCodeSizeUseCase: FetchQrCodeSizeUseCase = mockk()

    private val createPdfDocumentUseCase: CreatePdfDocumentUseCase = mockk()

    private val savedStateHandle: SavedStateHandle = SavedStateHandle()

    private lateinit var viewModel: PrintQRCodesViewModel

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    private val testScope = TestCoroutineScope(testDispatcher)

    private val productList = listOf(
        Product(
            id = 123,
            name = "Test product",
            expirationDate = "2023-08-14",
            hashCode = "12345678"
        )
    )
    private val idList = listOf(5)
    private val databaseMode = DatabaseMode.LOCAL
    private val qrCodeSize = QrCodeSize.BIG
    private val fileName = "sample.pdf"

    @Before
    fun setup() {
        savedStateHandle["productIdList"] = "123"
        coEvery { (observeDatabaseModeUseCase()) } returns flowOf(databaseMode)
        coEvery { (fetchQrCodeSizeUseCase()) } returns flowOf(qrCodeSize)
        coEvery { (getProductListByIdsProductsUseCase(databaseMode, idList)) } returns flowOf(
            productList
        )
        coEvery { (createPdfDocumentUseCase(any())) } returns fileName
        viewModel = PrintQRCodesViewModel(
            observeDatabaseModeUseCase,
            getProductListByIdsProductsUseCase,
            fetchQrCodeSizeUseCase,
            createPdfDocumentUseCase,
            savedStateHandle
        )
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun cleanup() {
        Dispatchers.resetMain()
    }

    @Test
    fun `fetchProducts updates uiState correctly`() = testScope.runBlockingTest {
        // Call the method to be tested
        viewModel.fetchProducts(idList)

        // Verify uiState updates
        viewModel.uiState.value.apply {
            assert(this.productList == productList)
            assert(this.qrCodesQuantity == productList.size)
            assert(this.sizeOfQrCodeNameResId == qrCodeSize.nameResId)
        }
    }

    @Test
    fun `onPrintCodesPermissionGranted updates uiState correctly`() {
        runBlocking {
            viewModel.fetchProducts(idList)
            viewModel.onPrintCodesPermissionGranted()
            delay(100)
            viewModel.uiState.value.apply {
                assert(this.pdfFileName == fileName)
                assert(this.navigateToPrintQrCodes)
            }
        }
    }

    @Test
    fun `clearPdfState clears pdf state in uiState`() {
        testScope.launch {
            viewModel.onPrintCodesPermissionGranted()

            viewModel.clearPdfState()
        }
        // Call the method to be tested

        // Verify pdf state is cleared
        viewModel.uiState.value.apply {
            assert(this.pdfFileName == null)
            assert(!this.navigateToSharePdfDocument)
            assert(!this.navigateToPrintQrCodes)
        }
    }


    @Test
    fun `onGoToPermissionSettings updates goToPermissionSettings`() {
        viewModel.onGoToPermissionSettings(true)

        viewModel.uiState.value.apply {
            assert(this.goToPermissionSettings)
        }
    }
}
