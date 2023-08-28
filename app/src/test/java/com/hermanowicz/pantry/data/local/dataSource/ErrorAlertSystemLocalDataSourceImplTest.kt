package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.ErrorDao
import com.hermanowicz.pantry.data.local.model.ErrorEntity
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ErrorAlertSystemLocalDataSourceImplTest {

    @Mock
    private lateinit var errorDao: ErrorDao

    private lateinit var errorAlertSystemLocalDataSource: ErrorAlertSystemLocalDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        errorAlertSystemLocalDataSource = ErrorAlertSystemLocalDataSourceImpl(errorDao)
    }

    @Test
    fun `test observeAll`() = runBlocking {
        val errors = listOf(ErrorEntity(1))
        `when`(errorDao.observeAll()).thenReturn(flowOf(errors))

        val result = errorAlertSystemLocalDataSource.observeAll().toList()

        assert(result[0] == errors)
    }

    @Test
    fun `test insert`() = runBlocking {
        val error = ErrorEntity(1)

        errorAlertSystemLocalDataSource.insert(error)

        verify(errorDao).insert(error)
    }
}
