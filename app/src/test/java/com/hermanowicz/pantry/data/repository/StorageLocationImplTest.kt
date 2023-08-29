package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.di.local.dataSource.StorageLocationLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.StorageLocationRemoteDataSource
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.single
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class StorageLocationImplTest {

    @Mock
    private lateinit var mockLocalDataSource: StorageLocationLocalDataSource

    @Mock
    private lateinit var mockRemoteDataSource: StorageLocationRemoteDataSource

    @Mock
    private lateinit var mockObserveDatabaseModeUseCase: ObserveDatabaseModeUseCase

    private lateinit var repository: StorageLocationRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = StorageLocationRepositoryImpl(
            mockLocalDataSource,
            mockRemoteDataSource,
            mockObserveDatabaseModeUseCase
        )
    }

    @Test
    fun `observeById returns correct data when databaseMode is LOCAL`() = runBlocking {
        val storageLocationId = 1
        val storageLocation = StorageLocationEntity(id = storageLocationId, name = "Test storageLocation")
        val expectedFlow = flowOf(storageLocation)

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeById(storageLocationId)).thenReturn(expectedFlow)

        val result = repository.observeById(storageLocationId, DatabaseMode.LOCAL).single()
        assert(result == storageLocation.toDomainModel())
    }

    @Test
    fun `observeAll returns correct data when databaseMode is LOCAL`() = runTest {
        val storageLocations = listOf(
            StorageLocationEntity(id = 1, name = "Storage Location 1"),
            StorageLocationEntity(id = 2, name = "Storage Location 2")
        )
        val expectedFlow = flowOf(storageLocations)

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeAll()).thenReturn(expectedFlow)

        repository.observeAll(DatabaseMode.LOCAL).collect {
            assert(it.size == 2)
        }
    }

    @Test
    fun `getLastId returns correct ID when categories exist`() = runBlocking {
        val storageLocations = listOf(
            StorageLocationEntity(id = 1, name = "Storage Location 1"),
            StorageLocationEntity(id = 2, name = "Storage Location 2")
        )

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeAll()).thenReturn(flowOf(storageLocations))

        val result = repository.getLastId(DatabaseMode.LOCAL)
        assert(result == 2)
    }

    @Test
    fun `getLastId returns -1 when no categories exist`() = runBlocking {
        val emptyCategories = emptyList<StorageLocationEntity>()

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeAll()).thenReturn(flowOf(emptyCategories))

        val result = repository.getLastId(DatabaseMode.LOCAL)
        assert(result == -1)
    }

    @Test
    fun `insert inserts into local database`() = runBlocking {
        val storageLocation = StorageLocation(id = 1, name = "Test storageLocation")

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))

        repository.insert(storageLocation)

        verify(mockLocalDataSource).insert(storageLocation.toEntityModel())
    }
}
