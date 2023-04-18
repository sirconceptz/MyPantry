package com.hermanowicz.pantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.google.firebase.FirebaseApp
import com.hermanowicz.pantry.navigation.features.AppNavHost
import com.hermanowicz.pantry.ui.theme.MyPantryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        FirebaseApp.initializeApp(applicationContext)
        setContent {
            MyPantryTheme {
                AppNavHost()
            }
        }
    }
}
