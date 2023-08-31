import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import com.hermanowicz.pantry.domain.storageLocation.UpdateStorageLocationUseCase
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class UpdateStorageLocationUseCaseTest {

    private lateinit var storageLocationRepository: StorageLocationRepository
    private lateinit var updateStorageLocationUseCase: UpdateStorageLocationUseCase

    @Before
    fun setup() {
        storageLocationRepository = mock(StorageLocationRepository::class.java)
        updateStorageLocationUseCase = UpdateStorageLocationUseCase(storageLocationRepository)
    }

    @Test
    fun `test invoke calls update on storage location repository`() = runTest {
        val storageLocation = StorageLocation(id = 123, name = "Test Location")

        updateStorageLocationUseCase.invoke(storageLocation)

        verify(storageLocationRepository).update(eq(storageLocation))
        verifyNoMoreInteractions(storageLocationRepository)
    }

    @Test
    fun `test invoke calls update on storage location repository with correct storage location`() = runTest {
        val storageLocation = StorageLocation(id = 456, name = "Another Location")

        updateStorageLocationUseCase.invoke(storageLocation)

        verify(storageLocationRepository).update(
            check {
                assert(it == storageLocation)
            }
        )
        verifyNoMoreInteractions(storageLocationRepository)
    }
}
