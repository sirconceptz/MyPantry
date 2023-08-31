import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.repository.StorageLocationRepository
import com.hermanowicz.pantry.domain.storageLocation.DeleteStorageLocationUseCase
import com.nhaarman.mockitokotlin2.check
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions

class DeleteStorageLocationUseCaseTest {

    private lateinit var storageLocationRepository: StorageLocationRepository
    private lateinit var deleteStorageLocationUseCase: DeleteStorageLocationUseCase

    @Before
    fun setup() {
        storageLocationRepository = mock(StorageLocationRepository::class.java)
        deleteStorageLocationUseCase = DeleteStorageLocationUseCase(storageLocationRepository)
    }

    @Test
    fun `test invoke calls delete on storage location repository`() = runTest {
        val storageLocation = StorageLocation(id = 123, name = "Test Location")

        deleteStorageLocationUseCase.invoke(storageLocation)

        verify(storageLocationRepository).delete(eq(storageLocation))
        verifyNoMoreInteractions(storageLocationRepository)
    }

    @Test
    fun `test invoke calls delete on storage location repository with correct storage location`() = runTest {
        val storageLocation = StorageLocation(id = 456, name = "Another Location")

        deleteStorageLocationUseCase.invoke(storageLocation)

        verify(storageLocationRepository).delete(
            check {
                assert(it == storageLocation)
            }
        )
        verifyNoMoreInteractions(storageLocationRepository)
    }
}
