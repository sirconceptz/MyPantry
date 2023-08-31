import com.hermanowicz.pantry.domain.product.CheckQuantityIsValidUseCase
import org.junit.Before
import org.junit.Test

class CheckQuantityIsValidUseCaseTest {

    private lateinit var checkQuantityIsValidUseCase: CheckQuantityIsValidUseCase

    @Before
    fun setup() {
        checkQuantityIsValidUseCase = CheckQuantityIsValidUseCase()
    }

    @Test
    fun `test invoke returns true when quantity is within valid range`() {
        val validQuantity = 500

        val result = checkQuantityIsValidUseCase.invoke(validQuantity)

        assert(result)
    }

    @Test
    fun `test invoke returns false when quantity is less than 1`() {
        val invalidQuantity = 0

        val result = checkQuantityIsValidUseCase.invoke(invalidQuantity)

        assert(!result)
    }

    @Test
    fun `test invoke returns false when quantity is greater than 10000`() {
        val invalidQuantity = 15000

        val result = checkQuantityIsValidUseCase.invoke(invalidQuantity)

        assert(!result)
    }

    @Test
    fun `test invoke returns true for null quantity`() {
        val nullQuantity: Int? = null

        val result = checkQuantityIsValidUseCase.invoke(nullQuantity)

        assert(result)
    }
}
