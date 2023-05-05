package com.hermanowicz.pantry.domain

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfDocument
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.PdfData
import com.hermanowicz.pantry.utils.PdfFile
import com.hermanowicz.pantry.utils.PrintQRData
import com.hermanowicz.pantry.utils.ProductLabel
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class CreatePdfDocumentUseCase @Inject constructor(
    private val fetchQrCodeSizeUseCase: FetchQrCodeSizeUseCase,
    @ApplicationContext private val context: Context
) : suspend (List<Product>) -> String {
    override suspend fun invoke(products: List<Product>): String {
        val pdfFileName = PdfFile.generatePdfFileName()
        val qrCodeSize = fetchQrCodeSizeUseCase().first()
        val productLabelList: List<ProductLabel> =
            PrintQRData.getProductLabelList(products, qrCodeSize)
        val pdfDocument: PdfDocument
        val pdfData = PdfData()
        pdfDocument = if (qrCodeSize == QrCodeSize.BIG) pdfData.createPdfDocumentBigQrCodes(
            productLabelList
        ) else pdfData.createPdfDocumentSmallQrCodes(
            productLabelList
        )
        PdfFile.savePdf(context, pdfDocument, pdfFileName)
        pdfDocument.close()
        return pdfFileName
    }
}