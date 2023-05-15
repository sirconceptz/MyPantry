package com.hermanowicz.pantry.di.local.dataSource

import com.hermanowicz.pantry.data.local.dataSource.ErrorAlertSystemLocalDataSourceImpl
import com.hermanowicz.pantry.data.local.model.ErrorEntity
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ErrorAlertSystemLocalDataSource {
    fun observeAll(): Flow<List<ErrorEntity>>
    suspend fun insert(error: ErrorEntity)

}

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorAlertSystemLocalDataSourceModule {

    @Binds
    abstract fun bindErrorAlertSystemLocalDataSource(
        errorAlertSystemLocalDataSourceImpl: ErrorAlertSystemLocalDataSourceImpl
    ): ErrorAlertSystemLocalDataSource
}
