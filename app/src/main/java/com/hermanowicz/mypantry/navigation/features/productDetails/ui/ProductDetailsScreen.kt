package com.hermanowicz.mypantry.navigation.features.productDetails.ui

import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.graphics.Color.Companion.White
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.divider.DividerCardInside
import com.hermanowicz.mypantry.components.common.loading.LoadingDialog
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.mypantry.navigation.features.productDetails.state.ProductDetailsUiState
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import com.hermanowicz.mypantry.ui.theme.Shapes
import timber.log.Timber

@Composable
fun ProductDetailsScreen(
    openDrawer: () -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val uiModel = updateUi(viewModel)

    TopBarScaffold(
        topBarText = stringResource(id = R.string.product_details),
        openDrawer = openDrawer
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                ProductDetailsView(uiModel.groupProduct)
            }
        }
    }
}

@Composable
fun ProductDetailsView(groupProduct: GroupProduct) {
    Card(
        modifier = Modifier.padding(top = LocalSpacing.current.medium),
        border = BorderStroke(width = LocalSpacing.current.line, color = Black),
        shape = Shapes.medium
    ) {
        Column(
            modifier = Modifier.background(White)
        ) {
            ProductDetailItem(
                label = stringResource(id = R.string.name),
                value = groupProduct.product.name
            )
            DividerCardInside()
            DividerCardInside()
            DividerCardInside()
        }
    }
}

@Composable
private fun updateUi(
    viewModel: ProductDetailsViewModel
): ProductDetailsModel {
    when (val state = viewModel.uiState.collectAsState().value) {
        is ProductDetailsUiState.Empty -> {
            Timber.d("My Pantry UI State - Empty")
            return ProductDetailsModel()
        }
        is ProductDetailsUiState.Loading -> {
            Timber.d("My Pantry UI State - Loading")
            LoadingDialog()
            return ProductDetailsModel()
        }
        is ProductDetailsUiState.Loaded -> {
            Timber.d("My Pantry UI State - Success")
            return state.data
        }
        is ProductDetailsUiState.Error -> {
            Timber.d("My Pantry UI State - Error")
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            return ProductDetailsModel()
        }
    }
}

@Composable
fun ProductDetailItem(label: String, value: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(LocalSpacing.current.medium),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = label,
            color = Black
        )
        Text(
            text = value,
            color = Black
        )
    }
}
