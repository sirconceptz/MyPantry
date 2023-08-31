import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import com.hermanowicz.pantry.domain.storageLocation.ObserveAllStorageLocationsUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ObserveAllStorageLocationsUseCaseTest {

    private lateinit var storageLocationRepository: StorageLocationRepository
    private lateinit var observeAllStorageLocationsUseCase: ObserveAllStorageLocationsUseCase

    @Before
    fun setup() {
        storageLocationRepository = mock(StorageLocationRepository::class.java)
        observeAllStorageLocationsUseCase = ObserveAllStorageLocationsUseCase(storageLocationRepository)
    }

    @Test
    fun `test invoke returns Flow of storage locations from repository`() = runTest {
        val expectedLocations = listOf(
            StorageLocation(id = 1, name = "Location1"),
            StorageLocation(id = 2, name = "Location2")
        )
        val databaseMode = DatabaseMode.LOCAL
        `when`(storageLocationRepository.observeAll(databaseMode)).thenReturn(flowOf(expectedLocations))

        val resultFlow: Flow<List<StorageLocation>> = observeAllStorageLocationsUseCase.invoke(databaseMode)
        val resultLocations: MutableList<StorageLocation> = mutableListOf()

        resultFlow.collect { locations ->
            resultLocations.addAll(locations)
        }

        assertEquals(expectedLocations, resultLocations)
    }
}
