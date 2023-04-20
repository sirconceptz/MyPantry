package com.hermanowicz.pantry.domain

import android.content.Context
import com.google.firebase.auth.FirebaseAuth
import com.hermanowicz.pantry.R
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class FetchUserEmailOrUnloggedUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : () -> String {
    override fun invoke(): String {
        val firebaseAuth = FirebaseAuth.getInstance()
        return firebaseAuth.currentUser?.email ?: context.getString(R.string.unlogged)
    }
}