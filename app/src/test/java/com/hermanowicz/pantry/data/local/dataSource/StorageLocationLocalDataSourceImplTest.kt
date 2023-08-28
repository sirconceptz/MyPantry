package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.StorageLocationDao
import com.hermanowicz.pantry.data.local.model.StorageLocationEntity
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class StorageLocationLocalDataSourceImplTest {

    @Mock
    private lateinit var storageLocationDao: StorageLocationDao

    private lateinit var storageLocationLocalDataSource: StorageLocationLocalDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        storageLocationLocalDataSource = StorageLocationLocalDataSourceImpl(storageLocationDao)
    }

    @Test
    fun `test observeAll`() = runBlocking {
        val categories = listOf(StorageLocationEntity(1, "Storage location 1"))
        `when`(storageLocationDao.observeAll()).thenReturn(flowOf(categories))

        val result = storageLocationLocalDataSource.observeAll().toList()

        assert(result[0] == categories)
    }

    @Test
    fun `test getAll`() = runBlocking {
        val categories = listOf(StorageLocationEntity(1, "Storage location 1"))
        `when`(storageLocationDao.getAll()).thenReturn(categories)

        val result = storageLocationLocalDataSource.getAll()

        assert(result == categories)
    }

    @Test
    fun `test observeById`() = runBlocking {
        val storageLocation = StorageLocationEntity(1, "Storage location 1")
        `when`(storageLocationDao.observeById(1)).thenReturn(flowOf(storageLocation))

        val result = storageLocationLocalDataSource.observeById(1).toList()

        assert(result[0] == storageLocation)
    }

    @Test
    fun `test insert`() = runBlocking {
        val storageLocation = StorageLocationEntity(1, "Storage location 1")

        storageLocationLocalDataSource.insert(storageLocation)

        verify(storageLocationDao).insert(storageLocation)
    }

    @Test
    fun `test update`() = runBlocking {
        val storageLocation = StorageLocationEntity(1, "storageLocation 1")

        storageLocationLocalDataSource.update(storageLocation)

        verify(storageLocationDao).update(storageLocation)
    }

    @Test
    fun `test delete`() = runBlocking {
        val storageLocation = StorageLocationEntity(1, "storageLocation 1")

        storageLocationLocalDataSource.delete(storageLocation)

        verify(storageLocationDao).delete(storageLocation)
    }

    @Test
    fun `test deleteAll`() = runBlocking {
        storageLocationLocalDataSource.deleteAll()

        verify(storageLocationDao).deleteAll()
    }
}
