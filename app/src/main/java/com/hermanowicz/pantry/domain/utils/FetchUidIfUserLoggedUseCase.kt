package com.hermanowicz.pantry.domain.utils

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class FetchUidIfUserLoggedUseCase @Inject constructor() : () -> String? {
    override fun invoke(): String? {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.uid
    }
}
