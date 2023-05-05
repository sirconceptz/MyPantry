package com.hermanowicz.pantry.navigation.features.myPantry

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.myPantry.ui.MyPantryScreen
import com.hermanowicz.pantry.navigation.features.myPantry.ui.MyPantryViewModel

@Composable
fun MyPantryRoute(
    onNavigateToProductDetails: (Pair<Int, String>) -> Unit,
    onNavigateToFilterProduct: () -> Unit,
    openDrawer: () -> Unit,
    myPantryViewModel: MyPantryViewModel
) {
    MyPantryScreen(
        openDrawer = openDrawer,
        onClickGroupProduct = onNavigateToProductDetails,
        onClickFilterProduct = onNavigateToFilterProduct,
        viewModel = myPantryViewModel
    )
}
