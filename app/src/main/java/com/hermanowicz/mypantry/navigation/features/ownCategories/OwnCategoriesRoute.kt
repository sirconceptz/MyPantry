package com.hermanowicz.mypantry.navigation.features.ownCategories

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import com.hermanowicz.mypantry.navigation.features.ownCategories.ui.OwnCategoriesScreen

@Composable
fun OwnCategoriesRoute(navController: NavHostController, openDrawer: () -> Unit) {
    OwnCategoriesScreen(
        openDrawer = openDrawer
    )
}
