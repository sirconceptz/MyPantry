package com.hermanowicz.mypantry.navigation.features.myPantry.ui

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
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.cards.GroupProductItemCard
import com.hermanowicz.mypantry.components.common.loading.LoadingDialog
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.mypantry.data.model.GroupProduct
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryProductsUiState
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import timber.log.Timber

@Composable
fun MyPantryScreen(
    viewModel: MyPantryViewModel = hiltViewModel(),
    onClickGroupProduct: (Int) -> Unit,
    onClickFilterProduct: () -> Unit,
    openDrawer: () -> Unit
) {
    val uiModel = updateUi(viewModel)

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
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                ShowProducts(uiModel.groupsProduct, onClickGroupProduct)
            }
        }
    }
}

@Composable
private fun updateUi(
    viewModel: MyPantryViewModel
): MyPantryModel {
    when (val state = viewModel.uiState.collectAsState().value) {
        is MyPantryProductsUiState.Empty -> {
            Timber.d("My Pantry UI State - Empty")
            return MyPantryModel()
        }
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
fun ShowProducts(groupsProduct: List<GroupProduct>, onClickGroupProduct: (Int) -> Unit) {
    groupsProduct.forEach { groupProduct ->
        GroupProductItemCard(groupProduct = groupProduct) { onClickGroupProduct(it) }
    }
}
