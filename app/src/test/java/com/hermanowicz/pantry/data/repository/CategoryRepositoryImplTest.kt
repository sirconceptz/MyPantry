package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.local.dataSource.CategoryLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.CategoryRemoteDataSource
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

class CategoryRepositoryImplTest {

    @Mock
    private lateinit var mockLocalDataSource: CategoryLocalDataSource

    @Mock
    private lateinit var mockRemoteDataSource: CategoryRemoteDataSource

    @Mock
    private lateinit var mockObserveDatabaseModeUseCase: ObserveDatabaseModeUseCase

    private lateinit var repository: CategoryRepositoryImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        repository = CategoryRepositoryImpl(
            mockLocalDataSource,
            mockRemoteDataSource,
            mockObserveDatabaseModeUseCase
        )
    }

    @Test
    fun `observeById returns correct data when databaseMode is LOCAL`() = runBlocking {
        val categoryId = 1
        val category = CategoryEntity(id = categoryId, name = "Test Category")
        val expectedFlow = flowOf(category)

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeById(categoryId)).thenReturn(expectedFlow)

        val result = repository.observeById(categoryId, DatabaseMode.LOCAL).single()
        assert(result == category.toDomainModel())
    }

    @Test
    fun `observeAll returns correct data when databaseMode is LOCAL`() = runTest {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Category 1"),
            CategoryEntity(id = 2, name = "Category 2")
        )
        val expectedFlow = flowOf(categories)

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeAll()).thenReturn(expectedFlow)

        repository.observeAll(DatabaseMode.LOCAL).collect {
            assert(it.size == 2)
        }
    }

    @Test
    fun `getLastId returns correct ID when categories exist`() = runBlocking {
        val categories = listOf(
            CategoryEntity(id = 1, name = "Category 1"),
            CategoryEntity(id = 2, name = "Category 2")
        )

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeAll()).thenReturn(flowOf(categories))

        val result = repository.getLastId(DatabaseMode.LOCAL)
        assert(result == 2)
    }

    @Test
    fun `getLastId returns -1 when no categories exist`() = runBlocking {
        val emptyCategories = emptyList<CategoryEntity>()

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))
        `when`(mockLocalDataSource.observeAll()).thenReturn(flowOf(emptyCategories))

        val result = repository.getLastId(DatabaseMode.LOCAL)
        assert(result == -1)
    }

    @Test
    fun `insert inserts into local database`() = runBlocking {
        val category = Category(id = 1, name = "Test Category")

        `when`(mockObserveDatabaseModeUseCase()).thenReturn(flowOf(DatabaseMode.LOCAL))

        repository.insert(category)

        verify(mockLocalDataSource).insert(category.toEntityModel())
    }
}
