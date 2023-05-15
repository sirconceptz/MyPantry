package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.ErrorDao
import com.hermanowicz.pantry.data.local.model.ErrorEntity
import com.hermanowicz.pantry.di.local.dataSource.ErrorAlertSystemLocalDataSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorAlertSystemLocalDataSourceImpl @Inject constructor(
    private val errorDao: ErrorDao
) : ErrorAlertSystemLocalDataSource {
    override fun observeAll(): Flow<List<ErrorEntity>> {
        return errorDao.observeAll()
    }

    override suspend fun insert(error: ErrorEntity) {
        errorDao.insert(error)
    }
}
