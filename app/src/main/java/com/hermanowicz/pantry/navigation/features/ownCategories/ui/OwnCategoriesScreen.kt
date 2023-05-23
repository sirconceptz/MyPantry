package com.hermanowicz.pantry.navigation.features.ownCategories.ui

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
import com.hermanowicz.pantry.R
import com.hermanowicz.pantry.components.common.cards.CategoryItemCard
import com.hermanowicz.pantry.components.common.dialog.DialogItem
import com.hermanowicz.pantry.components.common.loading.LoadingDialog
import com.hermanowicz.pantry.components.common.topBarScaffold.TopBarScaffold
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesModel
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesUiState
import com.hermanowicz.pantry.ui.theme.LocalSpacing
import timber.log.Timber

@Composable
fun OwnCategoriesScreen(
    openDrawer: () -> Unit,
    viewModel: OwnCategoriesViewModel
) {
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
            DialogItem(
                onDismissRequest = { viewModel.onShowDialogAddNewCategory(false) },
                label = stringResource(id = R.string.add_new_category),
                name = categoriesState.name,
                description = categoriesState.description,
                onNameChange = { viewModel.onAddCategoryNameChange(it) },
                onDescriptionChange = { viewModel.onAddCategoryDescriptionChange(it) },
                onSaveClick = { viewModel.onClickSaveCategory() }
            )
        }
        if (categoriesState.showDialogEditCategory) {
            DialogItem(
                onDismissRequest = { viewModel.onHideDialogEditCategory() },
                label = stringResource(id = R.string.edit_category),
                name = categoriesState.editedCategory.name,
                description = categoriesState.editedCategory.description,
                onNameChange = { viewModel.onEditCategoryNameChange(it) },
                onDescriptionChange = { viewModel.onEditCategoryDescriptionChange(it) },
                onSaveClick = { viewModel.onSaveEditedCategory() }
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
                    onClickEditCategory = {
                        viewModel.onShowEditCategory(it)
                    },
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
    onClickEditCategory: (Category) -> Unit,
    onClickDeleteCategory: (Category) -> Unit,
    isEditMode: Boolean
) {
    categoryList.forEach { category ->
        CategoryItemCard(
            category,
            isEditMode,
            onClickEditCategory = onClickEditCategory,
            onClickDeleteCategory = onClickDeleteCategory
        )
    }
}

@Composable
private fun updateCategoriesModel(
    viewModel: OwnCategoriesViewModel
): CategoriesModel {
    when (val state = viewModel.uiState.collectAsState().value) {
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
