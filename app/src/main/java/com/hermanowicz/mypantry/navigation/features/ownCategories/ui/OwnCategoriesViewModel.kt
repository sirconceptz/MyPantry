package com.hermanowicz.mypantry.navigation.features.ownCategories.ui

import androidx.lifecycle.ViewModel
import com.hermanowicz.mypantry.navigation.features.ownCategories.state.CategoriesState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class OwnCategoriesViewModel @Inject constructor() : ViewModel() {

    private val _categoriesState = MutableStateFlow(CategoriesState())
    var categoriesState: StateFlow<CategoriesState> =
        _categoriesState.asStateFlow()

    fun onNameChange(name: String) {
        _categoriesState.update { it.copy(name = name) }
    }

    fun onDescriptionChange(description: String) {
        _categoriesState.update { it.copy(description = description) }
    }

    fun onClickSaveStorageLocations() {
        // todo: impelement saving
    }

    fun onShowDialogAddNewCategory(isActive: Boolean) {
        _categoriesState.update { it.copy(showDialogAddNewCategory = isActive) }
    }
}
