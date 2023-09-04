package com.hermanowicz.pantry.utils

import android.Manifest
import android.content.ContentResolver
import android.content.ContentValues
import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import android.net.Uri
import android.os.Build
import android.os.Environment
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.rule.GrantPermissionRule
import com.hermanowicz.pantry.MainActivity
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.io.ByteArrayOutputStream
import java.io.File

@RunWith(AndroidJUnit4::class)
class PdfFileTest {
    private lateinit var context: Context
    private lateinit var pdfDocument: PdfDocument

    @get:Rule
    val activityRule = createAndroidComposeRule<MainActivity>()

    /*@get:Rule
    val permissionRule: GrantPermissionRule = GrantPermissionRule.grant(
        Manifest.permission.WRITE_EXTERNAL_STORAGE,
        Manifest.permission.ACCESS_MEDIA_LOCATION,
        //Manifest.permission.READ_EXTERNAL_STORAGE
    )*/

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        context = activityRule.activity.applicationContext
        pdfDocument = createTestPdfDocument()
    }

    private fun createTestPdfDocument(): PdfDocument {
        val pdfDocument = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(100, 100, 1).create()
        val page = pdfDocument.startPage(pageInfo)
        page.canvas.drawText("Test PDF Content", 10f, 10f, Paint())
        pdfDocument.finishPage(page)
        return pdfDocument
    }

    @Test
    fun testSavePdf() {
        val resolver = mock(ContentResolver::class.java)
        val outputStream = ByteArrayOutputStream()

        `when`(context.contentResolver).thenReturn(resolver)
        `when`(resolver.openOutputStream(any(Uri::class.java))).thenReturn(outputStream)

        val fileName = "test.pdf"

        PdfFile.savePdf(context, pdfDocument, fileName)

        // Verify that the appropriate methods were called
        verify(resolver).insert(any(Uri::class.java), any(ContentValues::class.java))
        verify(outputStream).close()

        // Check that the PDF was written to the OutputStream
        val outputStreamBytes = outputStream.toByteArray()
        assertEquals(28, outputStreamBytes.size) // Check the size as an example

        // Clean up - delete the test PDF file
        val pdfFile = PdfFile.getPdfFile(fileName)
        pdfFile.delete()
    }

    @Test
    fun testGetPdfFile() {
        val fileName = "test.pdf"

        val pdfFile = PdfFile.getPdfFile(fileName)

        val expectedPath = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            Environment.getExternalStorageDirectory()
                .toString() + File.separator + Constants.PDF_PATH + File.separator + fileName
        } else {
            Environment.getExternalStorageDirectory().toString() + File.separator + fileName
        }

        assertEquals(expectedPath, pdfFile.absolutePath)
    }
}
