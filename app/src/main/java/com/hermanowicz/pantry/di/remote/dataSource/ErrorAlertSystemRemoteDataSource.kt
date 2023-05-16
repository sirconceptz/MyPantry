package com.hermanowicz.pantry.di.remote.dataSource

import com.hermanowicz.pantry.data.local.model.CategoryEntity
import com.hermanowicz.pantry.data.model.errorAlertSystem.WPError
import com.hermanowicz.pantry.data.remote.dataSource.CategoryRemoteDataSourceImpl
import com.hermanowicz.pantry.data.remote.dataSource.ErrorAlertSystemRemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ErrorAlertSystemRemoteDataSource {
    suspend fun observeAll(): List<WPError>
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ErrorAlertSystemRemoteDataSourceModule {

    @Binds
    abstract fun bindCategoryRemoteDataSource(
        errorAlertSystemRemoteDataSourceImpl: ErrorAlertSystemRemoteDataSourceImpl
    ): ErrorAlertSystemRemoteDataSource
}
