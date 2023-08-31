import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.domain.product.CheckIsProductsHashcodeCorrectUseCase
import org.junit.Before
import org.junit.Test

class CheckIsProductsHashcodeCorrectUseCaseTest {

    private lateinit var checkIsProductsHashcodeCorrectUseCase: CheckIsProductsHashcodeCorrectUseCase

    @Before
    fun setup() {
        checkIsProductsHashcodeCorrectUseCase = CheckIsProductsHashcodeCorrectUseCase()
    }

    @Test
    fun `test invoke returns true when id and hashcode match a product`() {
        val id = 123
        val hashcode = "abc123"
        val products = listOf(
            Product(id = 123, name = "Product 1", hashCode = "abc123"),
            Product(id = 456, name = "Product 2", hashCode = "def456")
        )

        val result = checkIsProductsHashcodeCorrectUseCase.invoke(id, hashcode, products)

        assert(result)
    }

    @Test
    fun `test invoke returns false when id and hashcode do not match any product`() {
        val id = 789
        val hashcode = "xyz789"
        val products = listOf(
            Product(id = 123, name = "Product 1", hashCode = "abc123"),
            Product(id = 456, name = "Product 2", hashCode = "def456")
        )

        val result = checkIsProductsHashcodeCorrectUseCase.invoke(id, hashcode, products)

        assert(!result)
    }

    @Test
    fun `test invoke returns false when products list is empty`() {
        val id = 123
        val hashcode = "abc123"
        val emptyProductsList: List<Product> = emptyList()

        val result = checkIsProductsHashcodeCorrectUseCase.invoke(id, hashcode, emptyProductsList)

        assert(!result)
    }
}
