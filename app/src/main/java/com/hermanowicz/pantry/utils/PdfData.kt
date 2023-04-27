package com.hermanowicz.pantry.utils

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.graphics.pdf.PdfDocument.PageInfo

class PdfData {
    var pdfDocument = PdfDocument()
    var canvasPaint = Paint()
    var textPaint = Paint()
    private var pageNumber = 1
    private var widthCounter = 0
    private var topCounter = 0
    private var codesOnPageCounter = 0
    private var pageInfo: PageInfo
    private var page: PdfDocument.Page
    private var canvas: Canvas

    init {
        canvasPaint.color = Color.WHITE
        canvasPaint.style = Paint.Style.FILL
        textPaint.color = Color.BLACK
        textPaint.textSize = 8f
        pageInfo = PageInfo.Builder(595, 842, pageNumber).create()
        page = pdfDocument.startPage(pageInfo)
        canvas = page.canvas
        canvas.drawPaint(canvasPaint)
    }

    private fun createNewPage() {
        pageNumber++
        pageInfo = PageInfo.Builder(595, 842, pageNumber).create()
        pdfDocument.finishPage(page)
        page = pdfDocument.startPage(pageInfo)
        canvas = page.canvas
        canvas.drawPaint(canvasPaint)
        codesOnPageCounter = 0
        widthCounter = 0
        topCounter = 0
    }

    fun createPdfDocumentBigQrCodes(
        productLabelList: List<ProductLabel>
    ): PdfDocument {
        productLabelList.forEach {
            if (codesOnPageCounter == 20) {
                createNewPage()
            }
            if (widthCounter == 4) {
                topCounter++
                widthCounter = 0
            }
            canvas.drawBitmap(
                it.bitmap!!,
                (25 + widthCounter * 141).toFloat(),
                (64 + topCounter * 142).toFloat(),
                null
            )
            canvas.drawText(
                it.productName!!,
                (widthCounter * 141 + 40).toFloat(),
                (64 + topCounter * 142 + 112).toFloat(),
                textPaint
            )
            canvas.drawText(
                "EXP: " + it.expirationDate,
                (widthCounter * 141 + 40).toFloat(),
                (64 + topCounter * 142 + 122).toFloat(),
                textPaint
            )
            canvas.drawText(
                "PRO: " + it.productionDate,
                (widthCounter * 141 + 40).toFloat(),
                (64 + topCounter * 142 + 132).toFloat(),
                textPaint
            )
            widthCounter++
            codesOnPageCounter++
        }
        pdfDocument.finishPage(page)
        return pdfDocument
    }

    fun createPdfDocumentSmallQrCodes(
        productLabelList: List<ProductLabel>
    ): PdfDocument {
        productLabelList.forEach {
            if (codesOnPageCounter == 49) {
                createNewPage()
            }
            if (widthCounter == 7) {
                topCounter++
                widthCounter = 0
            }
            canvas.drawBitmap(
                it.bitmap!!,
                (widthCounter * 80).toFloat(),
                (topCounter * 120).toFloat(),
                null
            )
            canvas.drawText(
                it.productName!!,
                (widthCounter * 80 + 20).toFloat(),
                (topCounter * 120 + 90).toFloat(),
                textPaint
            )
            canvas.drawText(
                "EXP: " + it.expirationDate,
                (widthCounter * 80 + 20).toFloat(),
                (topCounter * 120 + 100).toFloat(),
                textPaint
            )
            widthCounter++
            codesOnPageCounter++
        }
        pdfDocument.finishPage(page)
        return pdfDocument
    }
}
