package com.hermanowicz.pantry.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.utils.enums.QrCodeSize
import com.journeyapps.barcodescanner.BarcodeEncoder
import org.json.JSONException
import org.json.JSONObject
import timber.log.Timber

/**
 * <h1>PrintQRData</h1>
 * A class used to prepare data from a list of products
 * that needed to print stickers with QR codes.
 *
 * @author Mateusz Hermanowicz
 */

private const val SMALL_QR_CODE_WIDTH = 100
private const val SMALL_QR_CODE_HEIGHT = 100
private const val BIG_QR_CODE_WIDTH = 120
private const val BIG_QR_CODE_HEIGHT = 120

class PrintQRData {

    companion object {
        fun getProductLabelList(
            productList: List<Product>,
            qrCodeSize: QrCodeSize
        ): List<ProductLabel> {
            val productLabelList = mutableListOf<ProductLabel>()
            val jsonObject = JSONObject()

            productList.forEach { product ->
                var qrCodeContent = ""
                var productName: String = product.name
                if (productName.length > 29) {
                    productName = productName.substring(0, 28) + "."
                }
                try {
                    if (product.id >= 0) {
                        jsonObject.put(
                            "product_id",
                            product.id
                        )
                    } else {
                        jsonObject.put("product_id", product.id)
                    }
                    jsonObject.put("hash_code", product.hashCode)
                    qrCodeContent = (jsonObject.toString())
                } catch (e: JSONException) {
                    Timber.tag("json").e(e.toString())
                }
                productLabelList.add(
                    ProductLabel(
                        productName = productName,
                        qrCodeContent = qrCodeContent,
                        bitmap = getBitmapQRCode(qrCodeContent, qrCodeSize),
                        expirationDate = product.expirationDate,
                        productionDate = product.productionDate
                    )
                )
            }
            return productLabelList.toList()
        }

        private fun getBitmapQRCode(textToQrCode: String, qrCodeSize: QrCodeSize): Bitmap {
            Timber.d(textToQrCode)
            val qrCodeWriter = QRCodeWriter()
            var bitMatrix: BitMatrix? = null
            try {
                bitMatrix = if (qrCodeSize == QrCodeSize.BIG) {
                    qrCodeWriter.encode(
                        textToQrCode,
                        BarcodeFormat.QR_CODE,
                        BIG_QR_CODE_WIDTH,
                        BIG_QR_CODE_HEIGHT
                    )
                } else {
                    qrCodeWriter.encode(
                        textToQrCode,
                        BarcodeFormat.QR_CODE,
                        SMALL_QR_CODE_WIDTH,
                        SMALL_QR_CODE_HEIGHT
                    )
                }
            } catch (e: WriterException) {
                Timber.e("QRCodeWriter", e.toString())
            }
            val barcodeEncoder = BarcodeEncoder()
            return barcodeEncoder.createBitmap(bitMatrix)
        }
    }
}
