import com.hermanowicz.pantry.data.model.Product
import com.hermanowicz.pantry.di.repository.NotificationRepository
import com.hermanowicz.pantry.domain.product.CreateNotificationForProductsUseCase
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

class CreateNotificationForProductsUseCaseTest {

    private lateinit var notificationRepository: NotificationRepository
    private lateinit var createNotificationForProductsUseCase: CreateNotificationForProductsUseCase

    @Before
    fun setup() {
        notificationRepository = mock(NotificationRepository::class.java)
        createNotificationForProductsUseCase = CreateNotificationForProductsUseCase(notificationRepository)
    }

    @Test
    fun `test invoke calls createNotification method with provided products`() = runBlocking {
        val products = listOf(
            Product(id = 1, name = "Product 1"),
            Product(id = 2, name = "Product 2")
        )

        createNotificationForProductsUseCase.invoke(products)

        verify(notificationRepository).createNotification(products)
    }
}
