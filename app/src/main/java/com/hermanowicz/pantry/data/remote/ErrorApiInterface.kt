package com.hermanowicz.pantry.data.remote

import com.hermanowicz.pantry.data.model.errorAlertSystem.WPError
import retrofit2.http.GET
import javax.inject.Singleton

@Singleton
interface ErrorApiInterface {

    @GET("posts?categories=32")
    suspend fun getErrorList(): List<WPError>
}
