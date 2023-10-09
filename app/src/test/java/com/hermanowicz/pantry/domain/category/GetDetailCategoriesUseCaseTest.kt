import android.content.Context
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.domain.category.GetDetailCategoriesUseCase
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ChooseCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.ReadyMealsCategoryTypes
import com.hermanowicz.pantry.utils.enums.category.detailCategory.VegetablesCategoryTypes
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetDetailCategoriesUseCaseTest {

    private lateinit var context: Context
    private lateinit var getDetailCategoriesUseCase: GetDetailCategoriesUseCase

    @Before
    fun setup() {
        context = mock()
        getDetailCategoriesUseCase = GetDetailCategoriesUseCase(context)
        whenever(context.getString(any())).thenReturn("Localized Text")
    }

    @Test
    fun `test invoke returns map of choose categories when main category is empty`() {
        val ownCategories = emptyList<Category>()
        whenever(context.getString(any())).thenReturn("Localized Text")

        val result = getDetailCategoriesUseCase.invoke(ownCategories, "")

        val expectedMap = ChooseCategoryTypes.values().associate { it.name to "Localized Text" }
        assertEquals(expectedMap, result)
    }

    @Test
    fun `test invoke returns map of choose categories when main category is CHOOSE`() {
        val ownCategories = emptyList<Category>()

        val result = getDetailCategoriesUseCase.invoke(ownCategories, "CHOOSE")

        val expectedMap = ChooseCategoryTypes.values().associate { it.name to "Localized Text" }
        assertEquals(expectedMap, result)
    }

    @Test
    fun `test invoke returns map of own categories with localized names when main category is OWN_CATEGORIES`() {
        val ownCategories = listOf(
            Category(name = "Category1"),
            Category(name = "Category2"),
            Category(name = "Category3")
        )

        val result = getDetailCategoriesUseCase.invoke(ownCategories, "OWN_CATEGORIES")

        val expectedMap = mapOf(
            "CHOOSE" to "Localized Text",
            "Category1" to "Category1",
            "Category2" to "Category2",
            "Category3" to "Category3"
        )
        assert(expectedMap == result)
    }

    @Test
    fun `test invoke returns map of ready meals categories when main category is READY_MEALS`() {
        val ownCategories = emptyList<Category>()

        val result = getDetailCategoriesUseCase.invoke(ownCategories, "READY_MEALS")

        val expectedMap = ReadyMealsCategoryTypes.values().associate { it.name to "Localized Text" }
        assert(expectedMap == result)
    }

    @Test
    fun `test invoke returns map of vegetable categories when main category is VEGETABLES`() {
        val ownCategories = emptyList<Category>()

        val result = getDetailCategoriesUseCase.invoke(ownCategories, "VEGETABLES")

        val expectedMap = VegetablesCategoryTypes.values().associate { it.name to "Localized Text" }
        assert(expectedMap == result)
    }

    @Test
    fun `test invoke returns choose when main category is not predefined`() {
        val ownCategories = listOf(
            Category(name = "Category1"),
            Category(name = "Category2")
        )
        whenever(context.getString(any())).thenReturn("Localized Text")

        val result = getDetailCategoriesUseCase.invoke(ownCategories, "")

        val expectedMap = mapOf(
            "CHOOSE" to "Localized Text"
        )
        assert(expectedMap == result)
    }
}
