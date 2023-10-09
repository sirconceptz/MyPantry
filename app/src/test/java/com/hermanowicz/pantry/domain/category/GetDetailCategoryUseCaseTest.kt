import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.category.GetDetailCategoryUseCase
import com.hermanowicz.pantry.utils.enums.category.detailCategory.StoreProductsCategoryTypes
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetDetailCategoryUseCaseTest {

    private lateinit var context: Context
    private lateinit var getDetailCategoryUseCase: GetDetailCategoryUseCase

    @Before
    fun setup() {
        context = mock()
        getDetailCategoryUseCase = GetDetailCategoryUseCase(context)
    }

    @Test
    fun `test invoke returns product detail category when main and detail categories are empty`() {
        val product = Product(detailCategory = "Custom Detail Category")

        val result = getDetailCategoryUseCase.invoke(emptyList(), product)

        assertEquals("Custom Detail Category", result)
    }

    @Test
    fun `test invoke returns matching category name from own categories`() {
        val ownCategories = listOf(
            Category(name = "Category1"),
            Category(name = "Category2"),
            Category(name = "Category3")
        )
        val product = Product(detailCategory = "Category2")

        val result = getDetailCategoryUseCase.invoke(ownCategories, product)

        assertEquals("Category2", result)
    }

    @Test
    fun `test invoke returns correct category from enum values`() {
        val product = Product(detailCategory = "StoreProductType1")
        val enumValue = StoreProductsCategoryTypes.STORE_PRODUCTS
        whenever(context.getString(enumValue.nameResId)).thenReturn("StoreProductType1")

        val result = getDetailCategoryUseCase.invoke(emptyList(), product)

        assertEquals("StoreProductType1", result)
    }

    @Test
    fun `test invoke returns product detail category when no match is found`() {
        val product = Product(detailCategory = "UnknownCategory")

        val result = getDetailCategoryUseCase.invoke(emptyList(), product)

        assertEquals("UnknownCategory", result)
    }
}
