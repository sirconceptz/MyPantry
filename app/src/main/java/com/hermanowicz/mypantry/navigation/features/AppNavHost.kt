package com.hermanowicz.mypantry.navigation.features

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hermanowicz.mypantry.navigation.features.myPantry.MyPantryRoute

@Composable
fun AppNavHost () {
        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = MyPantryScreens.MyPantry.route
        ) {
            composable(route = MyPantryScreens.MyPantry.route) {
                MyPantryRoute()
            }
        }
}