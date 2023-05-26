package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.model.ErrorResponse
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ErrorRepository {

    @GET("errors")
    suspend fun fetchAllErrors(): List<ErrorResponse>
}
