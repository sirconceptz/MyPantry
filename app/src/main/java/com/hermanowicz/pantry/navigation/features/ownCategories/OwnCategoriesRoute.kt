package com.hermanowicz.pantry.navigation.features.ownCategories

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.ownCategories.ui.OwnCategoriesScreen

@Composable
fun OwnCategoriesRoute(openDrawer: () -> Unit) {
    OwnCategoriesScreen(
        openDrawer = openDrawer
    )
}
