package com.hermanowicz.pantry.navigation.features.productDetails.ui

import android.widget.Toast
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.cards.CardWhiteBgWithBorder
import com.hermanowicz.pantry.components.common.dialog.DialogWarning
import com.hermanowicz.pantry.components.common.divider.DividerCardInside
import com.hermanowicz.pantry.components.common.dropdown.DropdownProductDetailsOptions
import com.hermanowicz.pantry.components.common.icons.EditIcon
import com.hermanowicz.pantry.components.common.icons.MenuIcon
import com.hermanowicz.pantry.components.common.image.PhotoBox
import com.hermanowicz.pantry.components.common.loading.LoadingDialog
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsModel
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsState
import com.hermanowicz.pantry.navigation.features.productDetails.state.ProductDetailsUiState
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import com.hermanowicz.pantry.utils.DateAndTimeConverter
import com.hermanowicz.pantry.utils.Permissions
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import timber.log.Timber

@Composable
fun ProductDetailsScreen(
    openDrawer: () -> Unit,
    onClickPrintQrCodes: (List<Int>) -> Unit,
    onClickEditProducts: (Int) -> Unit,
    onClickAddPhoto: (List<Int>) -> Unit,
    onNavigateToMyPantry: () -> Unit,
    viewModel: ProductDetailsViewModel
) {
    val uiModel = updateUi(viewModel)
    val state by viewModel.state.collectAsState()

    val cameraPermissions = Permissions.cameraPermissions

    val activityResultLauncher: ActivityResultLauncher<ScanOptions> =
        rememberLauncherForActivityResult(
            contract = ScanContract()
        ) { result ->
            viewModel.onScanBarcode(result)
        }

    val launcherAddBarcode =
        rememberLauncherForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) { requestedPermissions ->
            var isGranted = true
            for (permission in requestedPermissions) {
                if (!permission.value) {
                    isGranted = false
                }
            }
            if (isGranted) {
                activityResultLauncher.launch(viewModel.getScanOptions())
            } else {
                viewModel.onGoToPermissionSettings(true)
            }
        }

    LaunchedEffect(key1 = state.onAddBarcode) {
        if (state.onAddBarcode) {
            launcherAddBarcode.launch(cameraPermissions.toTypedArray())
            viewModel.onAddBarcode(false)
        }
    }

    if (state.onNavigateToAddPhoto) {
        onClickAddPhoto(uiModel.groupProduct.idList)
        viewModel.onNavigateToAddPhoto(false)
    }

    LaunchedEffect(key1 = state.onNavigateToEditProduct) {
        if (state.onNavigateToEditProduct) {
            onClickEditProducts(viewModel.productId)
            viewModel.onNavigateToEditProduct(false)
        }
    }

    LaunchedEffect(key1 = state.onNavigateToPrintQrCodes) {
        if (state.onNavigateToPrintQrCodes) {
            onClickPrintQrCodes(uiModel.groupProduct.idList)
            viewModel.onNavigateToPrintQrCodes(false)
        }
    }

    LaunchedEffect(key1 = state.onNavigateToMyPantry) {
        if (state.onNavigateToMyPantry) {
            onNavigateToMyPantry()
            viewModel.onNavigateToMyPantry(false)
        }
    }

    if (state.isDialogWarningDeleteVisible) {
        DialogWarning(
            label = stringResource(id = R.string.are_you_sure),
            warning = stringResource(id = R.string.delete_product_warning),
            onPositiveRequest = { viewModel.onDeleteProduct() },
            onDismissRequest = { viewModel.onShowDialogWarningDelete(false) }
        )
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.product_details),
        openDrawer = openDrawer,
        actions = {
            EditIcon(onClick = { onClickEditProducts(viewModel.productId) })
            MenuIcon(onClick = { viewModel.onShowMenu(true) })
            DropdownProductDetailsOptions(
                expanded = state.isMenuVisible,
                onShow = { viewModel.onShowMenu(!state.isMenuVisible) },
                onSelect = { viewModel.onSelect(it) }
            )
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                ProductDetailsView(uiModel.groupProduct, state)
            }
        }
    }
}

@Composable
fun ProductDetailsView(groupProduct: GroupProduct, state: ProductDetailsState) {
    if (state.photoPreview != null) {
        PhotoBox(state.photoPreview)
    }
    CardWhiteBgWithBorder(
        modifier = Modifier.padding(vertical = LocalSpacing.current.medium)
    ) {
        ProductDetailItem(
            label = stringResource(id = R.string.name),
            value = groupProduct.product.name
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.quantity),
            value = groupProduct.quantity.toString()
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
            label = stringResource(id = R.string.storage_location),
            value = groupProduct.product.storageLocation
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.production_date),
            value = DateAndTimeConverter.dateToVisibleWithYear(groupProduct.product.productionDate)
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
            label = stringResource(id = R.string.taste),
            value = groupProduct.product.taste
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.vege),
            value = if (groupProduct.product.isVege) {
                stringResource(id = R.string.yes)
            } else {
                stringResource(
                    id = R.string.no
                )
            }
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.bio),
            value = if (groupProduct.product.isBio) {
                stringResource(id = R.string.yes)
            } else {
                stringResource(
                    id = R.string.no
                )
            }
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.sugar),
            value = if (groupProduct.product.hasSugar) {
                stringResource(id = R.string.yes)
            } else {
                stringResource(
                    id = R.string.no
                )
            }
        )
        DividerCardInside()
        ProductDetailItem(
            label = stringResource(id = R.string.salt),
            value = if (groupProduct.product.hasSalt) {
                stringResource(id = R.string.yes)
            } else {
                stringResource(
                    id = R.string.no
                )
            }
        )
    }
}

@Composable
private fun updateUi(
    viewModel: ProductDetailsViewModel
): ProductDetailsModel {
    when (val state = viewModel.uiState.collectAsState().value) {
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
            Toast.makeText(
                LocalContext.current,
                stringResource(id = R.string.error_product_not_found),
                Toast.LENGTH_LONG
            ).show()
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
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth(),
            text = label
        )
        Text(
            modifier = Modifier
                .weight(0.5f)
                .fillMaxWidth(),
            text = value,
            textAlign = TextAlign.End
        )
    }
}
