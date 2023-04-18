package com.hermanowicz.pantry.navigation.features.filterProduct

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.filterProduct.ui.FilterProductScreen
import com.hermanowicz.pantry.navigation.features.myPantry.ui.MyPantryViewModel

@Composable
fun FilterProductRoute(
    onNavigateBack: () -> Unit,
    openDrawer: () -> Unit,
    myPantryViewModel: MyPantryViewModel
) {
    FilterProductScreen(
        openDrawer = openDrawer,
        viewModel = myPantryViewModel,
        onNavigateBack = onNavigateBack
    )
}
