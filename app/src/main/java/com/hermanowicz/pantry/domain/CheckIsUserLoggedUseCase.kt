package com.hermanowicz.pantry.domain

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class CheckIsUserLoggedUseCase @Inject constructor() : () -> Boolean {
    override fun invoke(): Boolean {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.email?.isNotEmpty() ?: false

    }
}