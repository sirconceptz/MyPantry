package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.local.model.ErrorEntity
import com.hermanowicz.pantry.di.local.dataSource.ErrorAlertSystemLocalDataSource
import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorAlertSystemRepositoryImpl @Inject constructor(
    private val localDataSource: ErrorAlertSystemLocalDataSource
) : ErrorAlertSystemRepository {
    override fun observeAll(): Flow<List<Int>> {
        return localDataSource.observeAll().map { it.map { error -> error.errorCode } }
    }

    override suspend fun insert(errorCode: Int) {
        localDataSource.insert(ErrorEntity(errorCode = errorCode))
    }
}