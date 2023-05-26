package com.hermanowicz.pantry.utils

import android.content.ContentValues
import android.content.Context
import android.graphics.pdf.PdfDocument
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import com.hermanowicz.pantry.utils.Constants.PDF_PATH
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class PdfFile {
    companion object {
        fun savePdf(
            context: Context,
            pdfDocument: PdfDocument,
            fileName: String
        ) {
            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val resolver = context.contentResolver
                    val contentValues = ContentValues()
                    contentValues.put(MediaStore.Files.FileColumns.DISPLAY_NAME, fileName)
                    contentValues.put(MediaStore.Files.FileColumns.MIME_TYPE, "application/pdf")
                    contentValues.put(MediaStore.Files.FileColumns.RELATIVE_PATH, PDF_PATH)
                    val target = MediaStore.Downloads.getContentUri("external")
                    val pdfUri = resolver.insert(target, contentValues)
                    val fos = resolver.openOutputStream(pdfUri!!)
                    pdfDocument.writeTo(fos)
                    fos!!.close()
                } else {
                    val pdfOutputStream = FileOutputStream(
                        Environment.getExternalStorageDirectory()
                            .toString() + File.separator + fileName,
                        false
                    )
                    pdfDocument.writeTo(pdfOutputStream)
                    pdfOutputStream.close()
                }
            } catch (e: IOException) {
                Timber.e("Can't save the PDF file - $e")
            }
        }

        fun getPdfFile(fileName: String): File {
            val pdfFile: File = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                File(
                    (
                        Environment.getExternalStorageDirectory().toString() + File.separator +
                            PDF_PATH
                        ),
                    fileName
                )
            } else {
                File(
                    Environment.getExternalStorageDirectory().toString() + File.separator,
                    fileName
                )
            }
            return pdfFile
        }
    }
}
