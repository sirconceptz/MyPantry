package com.hermanowicz.pantry.navigation.features.splashScreen

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.splashScreen.ui.SplashScreen

@Composable
fun SplashScreenRoute(
    onNavigateToMyPantry: () -> Unit
) {
    SplashScreen(onNavigateToMyPantry = onNavigateToMyPantry)
}