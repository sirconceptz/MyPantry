import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.domain.category.DeleteCategoryUseCase
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class DeleteCategoryUseCaseTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var deleteCategoryUseCase: DeleteCategoryUseCase

    @Before
    fun setup() {
        categoryRepository = mock()
        deleteCategoryUseCase = DeleteCategoryUseCase(categoryRepository)
    }

    @Test
    fun `test invoke calls delete on category repository`() = runBlocking {
        val category = Category(id = 123, name = "Test Category")

        deleteCategoryUseCase.invoke(category)

        verify(categoryRepository).delete(eq(category))
        verifyNoMoreInteractions(categoryRepository)
    }

    @Test
    fun `test invoke calls delete on category repository with correct category`() = runBlocking {
        val category = Category(id = 456, name = "Another Category")

        deleteCategoryUseCase.invoke(category)

        verify(categoryRepository).delete(
            check {
                assert(it == category)
            }
        )
        verifyNoMoreInteractions(categoryRepository)
    }
}
