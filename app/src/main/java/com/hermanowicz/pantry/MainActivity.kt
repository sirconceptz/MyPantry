package com.hermanowicz.pantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase
import com.hermanowicz.pantry.domain.utils.FetchUidIfUserLoggedUseCase
import com.hermanowicz.pantry.navigation.features.AppNavHost
import com.hermanowicz.pantry.ui.theme.MyPantryTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private lateinit var analytics: FirebaseAnalytics

    @Inject
    lateinit var fetchUidIfUserLoggedUseCase: FetchUidIfUserLoggedUseCase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        setContent {
            MyPantryTheme {
                AppNavHost()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        keepSyncedDatabaseIfUserIsLogged(true)
    }

    override fun onPause() {
        keepSyncedDatabaseIfUserIsLogged(false)
        super.onPause()
    }

    private fun keepSyncedDatabaseIfUserIsLogged(keepSynced: Boolean) {
        val uid = fetchUidIfUserLoggedUseCase()
        if (uid?.isNotEmpty() == true) {
            Firebase.database.reference.child(uid).keepSynced(keepSynced)
        }
    }
}
