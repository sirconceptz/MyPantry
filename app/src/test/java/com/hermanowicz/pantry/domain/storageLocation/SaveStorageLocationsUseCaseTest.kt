import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import com.hermanowicz.pantry.domain.storageLocation.SaveStorageLocationsUseCase
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class SaveStorageLocationsUseCaseTest {

    private lateinit var storageLocationRepository: StorageLocationRepository
    private lateinit var saveStorageLocationUseCase: SaveStorageLocationsUseCase

    @Before
    fun setup() {
        storageLocationRepository = mock(StorageLocationRepository::class.java)
        saveStorageLocationUseCase = SaveStorageLocationsUseCase(storageLocationRepository)
    }

    @Test
    fun `test invoke calls insert on storage location repository`() = runBlocking {
        val storageLocation = StorageLocation(id = 123, name = "Test Location")

        saveStorageLocationUseCase.invoke(storageLocation)

        verify(storageLocationRepository).insert(eq(storageLocation))
        verifyNoMoreInteractions(storageLocationRepository)
    }

    @Test
    fun `test invoke calls insert on storage location repository with correct storage location`() = runBlocking {
        val storageLocation = StorageLocation(id = 456, name = "Another Location")

        saveStorageLocationUseCase.invoke(storageLocation)

        verify(storageLocationRepository).insert(
            check {
                assert(it == storageLocation)
            }
        )
        verifyNoMoreInteractions(storageLocationRepository)
    }
}
