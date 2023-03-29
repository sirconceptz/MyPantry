package com.hermanowicz.mypantry.navigation.features.ownCategories.ui

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import com.hermanowicz.mypantry.R
import com.hermanowicz.mypantry.components.common.dialog.DialogAddNewItem
import com.hermanowicz.mypantry.components.common.topBarScaffold.TopBarScaffold

@Composable
fun OwnCategoriesScreen(openDrawer: () -> Unit) {
    val viewModel: OwnCategoriesViewModel = hiltViewModel()
    val categoriesState by viewModel.categoriesState.collectAsState()
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
                onAddClick = { viewModel.onClickSaveStorageLocations() }
            )
        }
    }
}
