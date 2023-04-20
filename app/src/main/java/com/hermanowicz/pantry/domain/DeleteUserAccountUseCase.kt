package com.hermanowicz.pantry.domain

import com.google.firebase.auth.FirebaseAuth
import javax.inject.Inject

class DeleteUserAccountUseCase @Inject constructor(

) : () -> Unit {
    override fun invoke() {
        val firebaseAuth = FirebaseAuth.getInstance()
        firebaseAuth.currentUser?.delete()
    }
}