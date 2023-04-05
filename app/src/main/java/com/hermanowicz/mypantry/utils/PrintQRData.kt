package com.hermanowicz.mypantry.utils

import android.graphics.Bitmap
import com.google.zxing.BarcodeFormat
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.google.zxing.qrcode.QRCodeWriter
import com.hermanowicz.mypantry.data.model.Product
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
object PrintQRData {
    private const val SMALL_QR_CODE_WIDTH = 100
    private const val SMALL_QR_CODE_HEIGHT = 100
    private const val BIG_QR_CODE_WIDTH = 120
    private const val BIG_QR_CODE_HEIGHT = 120
    fun getTextToQRCodeList(productList: List<Product>?): ArrayList<String> {
        val textToQRCodeList = ArrayList<String>()
        val jsonObject = JSONObject()
        var counter = 0
        if (productList != null) {
            for (product in productList) {
                try {
                    if (product.id > 0) jsonObject.put(
                        "product_id",
                        product.id
                    ) else jsonObject.put("product_id", counter)
                    jsonObject.put("hash_code", product.hashCode)
                    textToQRCodeList.add(jsonObject.toString())
                } catch (e: JSONException) {
                    Timber.tag("json").e(e.toString())
                }
                counter++
            }
        }
        return textToQRCodeList
    }

    fun getNamesOfProductsList(productList: List<Product>?): ArrayList<String> {
        val namesOfProductsList = ArrayList<String>()
        if (productList != null) {
            for (product in productList) {
                val productName: String = product.name
                if (productName.length > 29) {
                    namesOfProductsList.add(productName.substring(0, 28) + ".")
                } else {
                    namesOfProductsList.add(productName)
                }
            }
        }
        return namesOfProductsList
    }

    fun getExpirationDatesList(productList: List<Product>?): ArrayList<String> {
        val expirationDatesList = ArrayList<String>()
        if (productList != null) {
            for (product in productList) {
                expirationDatesList.add(product.expirationDate)
            }
        }
        return expirationDatesList
    }

    fun getProductionDatesList(productList: List<Product>?): ArrayList<String> {
        val productionDatesList = ArrayList<String>()
        if (productList != null) {
            for (product in productList) {
                productionDatesList.add(product.productionDate)
            }
        }
        return productionDatesList
    }

    fun getQrCodeBitmapArray(
        textToQRCodeArray: ArrayList<String>?,
        isBigQrCode: Boolean
    ): ArrayList<Bitmap> {
        val qrCodeBitmapArray = ArrayList<Bitmap>()
        assert(textToQRCodeArray != null)
        for (i in textToQRCodeArray!!.indices) {
            qrCodeBitmapArray.add(getBitmapQRCode(textToQRCodeArray[i], isBigQrCode))
        }
        return qrCodeBitmapArray
    }

    fun getBitmapQRCode(textToQrCode: String, isBigQrCode: Boolean): Bitmap {
        val qrCodeWriter = QRCodeWriter()
        var bitMatrix: BitMatrix? = null
        try {
            bitMatrix = if (isBigQrCode) qrCodeWriter.encode(
                textToQrCode,
                BarcodeFormat.QR_CODE,
                BIG_QR_CODE_WIDTH,
                BIG_QR_CODE_HEIGHT
            ) else qrCodeWriter.encode(
                textToQrCode,
                BarcodeFormat.QR_CODE,
                SMALL_QR_CODE_WIDTH,
                SMALL_QR_CODE_HEIGHT
            )
        } catch (e: WriterException) {
            Timber.e("QRCodeWriter", e.toString())
        }
        val barcodeEncoder = BarcodeEncoder()
        return barcodeEncoder.createBitmap(bitMatrix)
    }
}
