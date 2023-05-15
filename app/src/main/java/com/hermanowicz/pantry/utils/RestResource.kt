package com.hermanowicz.pantry.utils

sealed class RestResource<T>(val data: T? = null, val message: String? = null) {
    class Success<T>(data: T) : RestResource<T>(data)
    class Error<T>(message: String, data: T? = null) : RestResource<T>(data, message)
    class Loading<T>(data: T? = null) : RestResource<T>(data)
}
