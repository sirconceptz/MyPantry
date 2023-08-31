import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.domain.category.UpdateCategoryUseCase
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class UpdateCategoryUseCaseTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var updateCategoryUseCase: UpdateCategoryUseCase

    @Before
    fun setup() {
        categoryRepository = mock(CategoryRepository::class.java)
        updateCategoryUseCase = UpdateCategoryUseCase(categoryRepository)
    }

    @Test
    fun `test invoke calls update on category repository`() = runBlocking {
        val category = Category(id = 123, name = "Test Category")

        updateCategoryUseCase.invoke(category)

        verify(categoryRepository).update(eq(category))
        verifyNoMoreInteractions(categoryRepository)
    }

    @Test
    fun `test invoke calls update on category repository with correct category`() = runBlocking {
        val category = Category(id = 456, name = "Another Category")

        updateCategoryUseCase.invoke(category)

        verify(categoryRepository).update(
            check {
                assert(it == category)
            }
        )
        verifyNoMoreInteractions(categoryRepository)
    }
}
