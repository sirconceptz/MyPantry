import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.domain.category.SaveCategoryUseCase
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class SaveCategoryUseCaseTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var saveCategoryUseCase: SaveCategoryUseCase

    @Before
    fun setup() {
        categoryRepository = mock(CategoryRepository::class.java)
        saveCategoryUseCase = SaveCategoryUseCase(categoryRepository)
    }

    @Test
    fun `test invoke calls insert on category repository`() = runBlocking {
        val category = Category(id = 123, name = "Test Category")

        saveCategoryUseCase.invoke(category)

        verify(categoryRepository).insert(eq(category))
        verifyNoMoreInteractions(categoryRepository)
    }

    @Test
    fun `test invoke calls insert on category repository with correct category`() = runBlocking {
        val category = Category(id = 456, name = "Another Category")

        saveCategoryUseCase.invoke(category)

        verify(categoryRepository).insert(
            check {
                assert(it == category)
            }
        )
        verifyNoMoreInteractions(categoryRepository)
    }
}
