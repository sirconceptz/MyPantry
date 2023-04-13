package com.hermanowicz.mypantry.navigation.features.myPantry

import androidx.compose.runtime.Composable
import com.hermanowicz.mypantry.navigation.features.myPantry.ui.MyPantryScreen
import com.hermanowicz.mypantry.navigation.features.myPantry.ui.MyPantryViewModel

@Composable
fun MyPantryRoute(
    onNavigateToProductDetails: (Int) -> Unit,
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
