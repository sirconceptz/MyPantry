package com.hermanowicz.pantry.navigation.features.productDetails

import androidx.compose.runtime.Composable
import com.hermanowicz.pantry.navigation.features.productDetails.ui.ProductDetailsScreen
import com.hermanowicz.pantry.navigation.features.productDetails.ui.ProductDetailsViewModel

@Composable
fun ProductDetailsRoute(
    onNavigateToEditProducts: (Int) -> Unit,
    onNavigateToAddPhoto: (List<Int>) -> Unit,
    onNavigateToPrintQrCodes: (List<Int>) -> Unit,
    onNavigateToMyPantry: () -> Unit,
    openDrawer: () -> Unit,
    viewModel: ProductDetailsViewModel
) {
    ProductDetailsScreen(
        openDrawer = openDrawer,
        onClickEditProducts = onNavigateToEditProducts,
        onNavigateToMyPantry = onNavigateToMyPantry,
        onClickPrintQrCodes = onNavigateToPrintQrCodes,
        onClickAddPhoto = onNavigateToAddPhoto,
        viewModel = viewModel
    )
}
