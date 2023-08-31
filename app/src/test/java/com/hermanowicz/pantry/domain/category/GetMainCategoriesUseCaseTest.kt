import android.content.Context
import com.hermanowicz.pantry.domain.category.GetMainCategoriesUseCase
import com.hermanowicz.pantry.utils.category.MainCategories
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.whenever
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class GetMainCategoriesUseCaseTest {

    private lateinit var context: Context
    private lateinit var getMainCategoriesUseCase: GetMainCategoriesUseCase

    @Before
    fun setup() {
        context = mock()
        getMainCategoriesUseCase = GetMainCategoriesUseCase(context)
    }

    @Test
    fun `test invoke returns map of main categories with localized names`() {
        val mainCategories = MainCategories.values()
        whenever(context.getString(any())).thenReturn("Localized Text")

        val result = getMainCategoriesUseCase.invoke()

        val expectedMap = mainCategories.associate { it.name to "Localized Text" }
        assertEquals(expectedMap, result)
    }
}
