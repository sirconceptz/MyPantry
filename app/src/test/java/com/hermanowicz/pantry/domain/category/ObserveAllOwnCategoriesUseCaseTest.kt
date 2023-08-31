import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ObserveAllOwnCategoriesUseCaseTest {

    private lateinit var categoryRepository: CategoryRepository
    private lateinit var observeAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase

    @Before
    fun setup() {
        categoryRepository = mock(CategoryRepository::class.java)
        observeAllOwnCategoriesUseCase = ObserveAllOwnCategoriesUseCase(categoryRepository)
    }

    @Test
    fun `test invoke returns Flow of categories from repository`() = runTest {
        val expectedCategories = listOf(
            Category(id = 1, name = "Category1"),
            Category(id = 2, name = "Category2")
        )
        val databaseMode = DatabaseMode.LOCAL
        `when`(categoryRepository.observeAll(databaseMode)).thenReturn(flowOf(expectedCategories))

        val resultFlow: Flow<List<Category>> = observeAllOwnCategoriesUseCase.invoke(databaseMode)
        val resultCategories: MutableList<Category> = mutableListOf()

        resultFlow.collect { categories ->
            resultCategories.addAll(categories)
        }

        assert(expectedCategories == resultCategories)
    }
}
