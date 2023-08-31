package com.hermanowicz.pantry.domain.product

import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.category.GetDetailCategoryUseCase
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import junit.framework.TestCase.assertEquals
import org.junit.Test


class ParseDeprecatedDatabaseProductsUseCaseTest {

    private val context: Context = mock()
    private val getDetailCategoryUseCase: GetDetailCategoryUseCase = mock()
    private val useCase = ParseDeprecatedDatabaseProductsUseCase(context, getDetailCategoryUseCase)

    @Test
    fun `test invoke returns parsed products`() {
        val product1 = Product(id = 1, taste = "SWEET", mainCategory = "VEGETABLES", detailCategory = "Carrots")
        val product2 = Product(id = 2, taste = "SOUR", mainCategory = "READY_MEALS", detailCategory = "Pizza")

        val category1 = Category(name = "Carrots", id = 1)
        val category2 = Category(name = "Pizza", id = 2)

        whenever(context.getString(any())).thenReturn("TranslatedText")
        whenever(getDetailCategoryUseCase.invoke(any(), any())).thenReturn("TranslatedDetailCategory")

        val parsedProducts = useCase(listOf(product1, product2), listOf(category1, category2))

        assertEquals("TranslatedText", parsedProducts[0].taste)
        assertEquals("TranslatedText", parsedProducts[0].mainCategory)
        assertEquals("TranslatedDetailCategory", parsedProducts[0].detailCategory)

        assertEquals("TranslatedText", parsedProducts[1].taste)
        assertEquals("TranslatedText", parsedProducts[1].mainCategory)
        assertEquals("TranslatedDetailCategory", parsedProducts[1].detailCategory)
    }

    @Test
    fun `test invoke handles exceptions and keeps original values`() {
        val product = Product(id = 1, taste = "UNKNOWN_TASTE", mainCategory = "UNKNOWN_CATEGORY", detailCategory = "Unknown")

        whenever(context.getString(any())).thenThrow(RuntimeException())
        whenever(getDetailCategoryUseCase.invoke(any(), any())).thenReturn("Unknown")

        val parsedProducts = useCase(listOf(product), emptyList())

        assertEquals("UNKNOWN_TASTE", parsedProducts[0].taste)
        assertEquals("UNKNOWN_CATEGORY", parsedProducts[0].mainCategory)
        assertEquals("Unknown", parsedProducts[0].detailCategory)
    }
}
