package com.hermanowicz.pantry.navigation.features.ownCategories

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.ownCategories.ui.OwnCategoriesScreen
import com.hermanowicz.pantry.navigation.features.ownCategories.ui.OwnCategoriesViewModel

@Composable
fun OwnCategoriesRoute(
    openDrawer: () -> Unit,
    viewModel: OwnCategoriesViewModel
) {
    OwnCategoriesScreen(
        openDrawer = openDrawer,
        viewModel = viewModel
    )
}
