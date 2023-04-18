package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.di.repository.UserRepository

class UserRepositoryImpl : UserRepository {

    override fun isAnonymousUser(): Boolean {
        //return (firebaseAuth.currentUser == null || firebaseAuth.currentUser!!.isAnonymous)
        return true
    }

}