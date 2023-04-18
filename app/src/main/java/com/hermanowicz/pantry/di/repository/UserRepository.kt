package com.hermanowicz.pantry.di.repository

interface UserRepository {
    fun isAnonymousUser(): Boolean
}