package com.hermanowicz.pantry.navigation.features.productDetails.ui

import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color.Companion.Black
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.cards.CardWhiteBgWithBorder
import com.hermanowicz.pantry.components.common.divider.DividerCardInside
import com.hermanowicz.pantry.components.common.icons.EditIcon
import com.hermanowicz.pantry.components.common.loading.LoadingDialog
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsUiState
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.utils.DateAndTimeConverter
import timber.log.Timber

@Composable
fun ProductDetailsScreen(
    openDrawer: () -> Unit,
    onClickEditProducts: (Int) -> Unit,
    viewModel: ProductDetailsViewModel = hiltViewModel()
) {
    val uiModel = updateUi(viewModel)

    TopBarScaffold(
        topBarText = stringResource(id = R.string.product_details),
        openDrawer = openDrawer,
        actions = {
            EditIcon(onClick = { onClickEditProducts(viewModel.productId) })
        }
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
    CardWhiteBgWithBorder(
        modifier = Modifier.padding(top = LocalSpacing.current.medium)
    ) {
        ProductDetailItem(
            label = stringResource(id = R.string.name),
            value = groupProduct.product.name
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.main_category),
            value = groupProduct.product.mainCategory
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.detail_category),
            value = groupProduct.product.detailCategory
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.expiration_date),
            value = DateAndTimeConverter.dateToVisibleWithYear(groupProduct.product.expirationDate)
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.production_date),
            value = DateAndTimeConverter.dateToVisibleWithYear(groupProduct.product.productionDate)
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.storage_locations),
            value = groupProduct.product.storageLocation
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.composition),
            value = groupProduct.product.composition
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.healing_properties),
            value = groupProduct.product.healingProperties
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.dosage),
            value = groupProduct.product.dosage
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.volume),
            value = if (groupProduct.product.volume == 0) "" else groupProduct.product.volume.toString()
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.weight),
            value = if (groupProduct.product.weight == 0) "" else groupProduct.product.weight.toString()
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.vege),
            value = if (groupProduct.product.isVege) stringResource(id = R.string.yes) else stringResource(
                id = R.string.no
            )
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.bio),
            value = if (groupProduct.product.isBio) stringResource(id = R.string.yes) else stringResource(
                id = R.string.no
            )
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.sugar),
            value = if (groupProduct.product.hasSugar) stringResource(id = R.string.yes) else stringResource(
                id = R.string.no
            )
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.salt),
            value = if (groupProduct.product.hasSalt) stringResource(id = R.string.yes) else stringResource(
                id = R.string.no
            )
        )
    }
}

@Composable
private fun updateUi(
    viewModel: ProductDetailsViewModel
): ProductDetailsModel {
    when (val state = viewModel.uiState.collectAsState().value) {
        is ProductDetailsUiState.Empty -> {
            Timber.d("Product Details UI State - Empty")
            return ProductDetailsModel()
        }
        is ProductDetailsUiState.Loading -> {
            Timber.d("Product Details UI State - Loading")
            LoadingDialog()
            return ProductDetailsModel()
        }
        is ProductDetailsUiState.Loaded -> {
            Timber.d("Product Details UI State - Success")
            return state.data
        }
        is ProductDetailsUiState.Error -> {
            Timber.d("Product Details UI State - Error")
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
