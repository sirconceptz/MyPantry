package com.hermanowicz.mypantry

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.hermanowicz.mypantry.navigation.features.AppNavHost
import com.hermanowicz.mypantry.ui.theme.MyPantryTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyPantryTheme {
                AppNavHost()
            }
        }
    }
}
