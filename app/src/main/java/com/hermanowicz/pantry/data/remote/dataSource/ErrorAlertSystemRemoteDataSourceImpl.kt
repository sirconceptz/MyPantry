package com.hermanowicz.pantry.data.remote.dataSource

import com.hermanowicz.pantry.data.model.errorAlertSystem.WPError
import com.hermanowicz.pantry.data.remote.ErrorApiInterface
import com.hermanowicz.pantry.di.remote.dataSource.ErrorAlertSystemRemoteDataSource
import javax.inject.Inject

class ErrorAlertSystemRemoteDataSourceImpl @Inject constructor(
    private val apiInterface: ErrorApiInterface
) : ErrorAlertSystemRemoteDataSource {
    override suspend fun observeAll(): List<WPError> {
        return apiInterface.getErrorList()
    }
}
