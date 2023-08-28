package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.CategoryDao
import com.hermanowicz.pantry.data.local.model.CategoryEntity
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class CategoryLocalDataSourceImplTest {

    @Mock
    private lateinit var categoryDao: CategoryDao

    private lateinit var categoryLocalDataSource: CategoryLocalDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        categoryLocalDataSource = CategoryLocalDataSourceImpl(categoryDao)
    }

    @Test
    fun `test observeAll`() = runBlocking {
        val categories = listOf(CategoryEntity(1, "Category 1"))
        `when`(categoryDao.observeAll()).thenReturn(flowOf(categories))

        val result = categoryLocalDataSource.observeAll().toList()

        assert(result[0] == categories)
    }

    @Test
    fun `test getAll`() = runBlocking {
        val categories = listOf(CategoryEntity(1, "Category 1"))
        `when`(categoryDao.getAll()).thenReturn(categories)

        val result = categoryLocalDataSource.getAll()

        assert(result == categories)
    }

    @Test
    fun `test observeById`() = runBlocking {
        val category = CategoryEntity(1, "Category 1")
        `when`(categoryDao.observeById(1)).thenReturn(flowOf(category))

        val result = categoryLocalDataSource.observeById(1).toList()

        assert(result[0] == category)
    }

    @Test
    fun `test insert`() = runBlocking {
        val category = CategoryEntity(1, "Category 1")

        categoryLocalDataSource.insert(category)

        verify(categoryDao).insert(category)
    }

    @Test
    fun `test update`() = runBlocking {
        val category = CategoryEntity(1, "Category 1")

        categoryLocalDataSource.update(category)

        verify(categoryDao).update(category)
    }

    @Test
    fun `test delete`() = runBlocking {
        val category = CategoryEntity(1, "Category 1")

        categoryLocalDataSource.delete(category)

        verify(categoryDao).delete(category)
    }

    @Test
    fun `test deleteAll`() = runBlocking {
        categoryLocalDataSource.deleteAll()

        verify(categoryDao).deleteAll()
    }
}
