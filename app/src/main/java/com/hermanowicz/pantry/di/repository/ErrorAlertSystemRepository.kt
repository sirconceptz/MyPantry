package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.repository.ErrorAlertSystemRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ErrorAlertSystemRepository {
    fun observeAll(): Flow<List<Int>>
    suspend fun insert(errorCode: Int)
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorAlertSystemRepositoryModule {

    @Binds
    abstract fun bindErrorAlertSystemLocalDataSource(
        errorAlertSystemRepositoryImpl: ErrorAlertSystemRepositoryImpl
    ): ErrorAlertSystemRepository
}
