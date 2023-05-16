package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.local.model.ErrorEntity
import com.hermanowicz.pantry.data.model.errorAlertSystem.ErrorAlert
import com.hermanowicz.pantry.di.local.dataSource.ErrorAlertSystemLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.ErrorAlertSystemRemoteDataSource
import com.hermanowicz.pantry.di.repository.ErrorAlertSystemRepository
import com.hermanowicz.pantry.domain.GetErrorCodeUseCase
import com.hermanowicz.pantry.domain.ParseHtmlToStringUseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ErrorAlertSystemRepositoryImpl @Inject constructor(
    private val localDataSource: ErrorAlertSystemLocalDataSource,
    private val remoteDataSource: ErrorAlertSystemRemoteDataSource,
    private val getErrorCodeUseCase: GetErrorCodeUseCase,
    private val parseHtmlToStringUseCase: ParseHtmlToStringUseCase
) : ErrorAlertSystemRepository {
    override fun observeAllDisplayedErrors(): Flow<List<Int>> {
        return localDataSource.observeAll().map { it.map { error -> error.errorCode } }
    }

    override suspend fun observeAllRemoteErrors(): List<ErrorAlert> {
        return try {
            remoteDataSource.observeAll().map { wpError ->
                val title = parseHtmlToStringUseCase(wpError.title?.rendered ?: "")
                val message = parseHtmlToStringUseCase(wpError.content?.rendered ?: "")
                val errorCode = getErrorCodeUseCase(title) ?: 0
                ErrorAlert(
                    errorCode = errorCode,
                    title = title,
                    message = message
                )
            }.filter { it.errorCode != 0 }
        } catch (e: Exception) {
            Timber.e("Retrofit - Fetching errors - " + e.message)
            emptyList()
        }
    }

    override suspend fun insert(errorCode: Int) {
        localDataSource.insert(ErrorEntity(errorCode = errorCode))
    }
}