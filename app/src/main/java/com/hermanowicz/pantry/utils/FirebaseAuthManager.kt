package com.hermanowicz.pantry.utils

import android.content.Intent
import com.firebase.ui.auth.FirebaseAuthUIActivityResultContract
import com.firebase.ui.auth.data.model.FirebaseAuthUIAuthenticationResult

interface FirebaseAuthManager {

    fun buildLoginIntent(): Intent
    fun buildLoginActivityResult(): FirebaseAuthUIActivityResultContract =
        FirebaseAuthUIActivityResultContract()

    fun onLoginResult(result: FirebaseAuthUIAuthenticationResult)
}