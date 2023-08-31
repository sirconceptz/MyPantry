import com.hermanowicz.pantry.di.repository.ProductRepository
import com.hermanowicz.pantry.domain.product.DeleteAllProductsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class DeleteAllProductsUseCaseTest {

    private lateinit var productRepository: ProductRepository
    private lateinit var deleteAllProductsUseCase: DeleteAllProductsUseCase

    @Before
    fun setup() {
        productRepository = mock(ProductRepository::class.java)
        deleteAllProductsUseCase = DeleteAllProductsUseCase(productRepository)
    }

    @Test
    fun `test invoke calls deleteAllCurrentDatabase method`() = runBlocking {
        deleteAllProductsUseCase.invoke()

        verify(productRepository).deleteAllCurrentDatabase()
    }
}
