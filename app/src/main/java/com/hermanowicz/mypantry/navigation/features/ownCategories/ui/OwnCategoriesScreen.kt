package com.hermanowicz.mypantry.navigation.features.ownCategories.ui

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.cards.CategoryItemCard
import com.hermanowicz.mypantry.components.common.dialog.DialogAddNewItem
import com.hermanowicz.mypantry.components.common.loading.LoadingDialog
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.navigation.features.ownCategories.state.CategoriesModel
import com.hermanowicz.mypantry.navigation.features.ownCategories.state.CategoriesUiState
import com.hermanowicz.mypantry.ui.theme.LocalSpacing
import timber.log.Timber

@Composable
fun OwnCategoriesScreen(openDrawer: () -> Unit) {
    val viewModel: OwnCategoriesViewModel = hiltViewModel()
    val categoriesState by viewModel.categoriesState.collectAsState()

    val categoriesModel = updateCategoriesModel(viewModel)

    if (categoriesModel.categories.isEmpty() && categoriesState.isEditMode)
        viewModel.onEditMode(false)

    TopBarScaffold(
        topBarText = stringResource(id = R.string.own_categories),
        openDrawer = openDrawer,
        actions = {
            IconButton(onClick = { viewModel.onShowDialogAddNewCategory(true) }, content = {
                Icon(
                    painter = painterResource(id = R.drawable.ic_add_item),
                    contentDescription = null,
                    tint = Color.White
                )
            })
            IconButton(
                onClick = { viewModel.onEditMode(!categoriesState.isEditMode) },
                content = {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_edit),
                        contentDescription = null,
                        tint = Color.White
                    )
                },
                enabled = categoriesModel.categories.isNotEmpty()
            )
        }
    ) {
        if (categoriesState.showDialogAddNewCategory) {
            DialogAddNewItem(
                onDismissRequest = { viewModel.onShowDialogAddNewCategory(false) },
                label = stringResource(id = R.string.add_new_category),
                name = categoriesState.name,
                description = categoriesState.description,
                onNameChange = { viewModel.onNameChange(it) },
                onDescriptionChange = { viewModel.onDescriptionChange(it) },
                onAddClick = { viewModel.onClickSaveCategory() }
            )
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = LocalSpacing.current.medium)
        ) {
            item {
                ShowCategories(
                    categoryList = categoriesModel.categories,
                    onClickDeleteCategory = {
                        viewModel.onDeleteCategory(it)
                    },
                    isEditMode = categoriesState.isEditMode
                )
            }
        }
    }
}

@Composable
private fun ShowCategories(
    categoryList: List<Category>,
    onClickDeleteCategory: (Category) -> Unit,
    isEditMode: Boolean
) {
    categoryList.forEach { category ->
        CategoryItemCard(category, isEditMode) { onClickDeleteCategory(it) }
    }
}

@Composable
private fun updateCategoriesModel(
    viewModel: OwnCategoriesViewModel
): CategoriesModel {
    when (val state = viewModel.uiState.collectAsState().value) {
        is CategoriesUiState.Empty -> {
            Timber.d("Storage Locations UI State - Empty")
            return CategoriesModel()
        }
        is CategoriesUiState.Loading -> {
            Timber.d("Storage Locations UI State - Loading")
            LoadingDialog()
            return CategoriesModel()
        }
        is CategoriesUiState.Loaded -> {
            Timber.d("Storage Locations UI State - Success")
            return state.data
        }
        is CategoriesUiState.Error -> {
            Timber.d("Storage Locations UI State - Error")
            Toast.makeText(LocalContext.current, "Error", Toast.LENGTH_SHORT).show()
            return CategoriesModel()
        }
    }
}
