package com.hermanowicz.pantry.navigation.features.myPantry.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.cards.GroupProductItemCard
import com.hermanowicz.pantry.components.common.dialog.DialogWarning
import com.hermanowicz.pantry.components.common.loading.LoadingDialog
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.data.model.GroupProduct
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.pantry.navigation.features.myPantry.state.MyPantryProductsUiState
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import timber.log.Timber

@Composable
fun MyPantryScreen(
    openDrawer: () -> Unit,
    onClickGroupProduct: (Pair<Int, String>) -> Unit,
    onClickFilterProduct: () -> Unit,
    viewModel: MyPantryViewModel

) {
    val uiModel = updateUi(viewModel)
    val errorSystemAlertSystemState by viewModel.errorAlertSystemState.collectAsState()

    errorSystemAlertSystemState.activeErrorList.forEach { error ->
        DialogWarning(
            label = error.title,
            warning = error.message,
            onPositiveRequest = { viewModel.saveErrorAsDisplayedAndCloseDialog(error) },
            onDismissRequest = { viewModel.saveErrorAsDisplayedAndCloseDialog(error) }
        )
    }

    TopBarScaffold(
        topBarText = stringResource(id = R.string.app_name),
        openDrawer = openDrawer,
        actions = {
            IconButton(onClick = onClickFilterProduct, content = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    contentDescription = null,
                    tint = Color.White
                )
            })
        }
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.small)
        ) {
            item {
                ShowProducts(uiModel.groupProductList, onClickGroupProduct)
            }
        }
    }
}

@Composable
private fun updateUi(
    viewModel: MyPantryViewModel
): MyPantryModel {
    when (val state = viewModel.uiState.collectAsState().value) {
        is MyPantryProductsUiState.Loading -> {
            Timber.d("My Pantry UI State - Loading")
            LoadingDialog()
            return MyPantryModel()
        }

        is MyPantryProductsUiState.Loaded -> {
            Timber.d("My Pantry UI State - Success")
            return state.data
        }

        is MyPantryProductsUiState.Error -> {
            Timber.d("My Pantry UI State - Error")
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            return MyPantryModel()
        }
    }
}

@Composable
fun ShowProducts(
    groupsProduct: List<GroupProduct>,
    onClickGroupProduct: (Pair<Int, String>) -> Unit
) {
    groupsProduct.forEach { groupProduct ->
        GroupProductItemCard(groupProduct = groupProduct) { onClickGroupProduct(it) }
    }
}
