package com.hermanowicz.pantry.navigation.features.ownCategories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.domain.category.DeleteCategoryUseCase
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.category.SaveCategoryUseCase
import com.hermanowicz.pantry.domain.category.UpdateCategoryUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesModel
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesState
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OwnCategoriesViewModel @Inject constructor(
    private val observeAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase,
    private val saveCategoryUseCase: SaveCategoryUseCase,
    private val updateCategoryUseCase: UpdateCategoryUseCase,
    private val deleteCategoryUseCase: DeleteCategoryUseCase,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val uiState: StateFlow<CategoriesUiState> = observeDatabaseModeUseCase()
        .flatMapLatest { databaseMode ->
            observeAllOwnCategoriesUseCase(databaseMode)
        }
        .map { categories ->
            CategoriesUiState.Loaded(
                CategoriesModel(
                    categories = categories,
                    loadingVisible = false
                )
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = CategoriesUiState.Loading
        )

    private val _categoriesState = MutableStateFlow(CategoriesState())
    var categoriesState: StateFlow<CategoriesState> =
        _categoriesState.asStateFlow()

    fun onClickSaveCategory() {
        if (categoriesState.value.name.length in 3..40) {
            val category = Category(
                name = categoriesState.value.name,
                description = categoriesState.value.description
            )
            viewModelScope.launch(Dispatchers.IO) {
                saveCategoryUseCase(category)
            }
            onShowDialogAddNewCategory(false)
            showErrorWrongName(false)
            clearTextfields()
        } else {
            showErrorWrongName(true)
        }
    }

    private fun showErrorWrongName(bool: Boolean) {
        _categoriesState.update { it.copy(showErrorWrongName = bool) }
    }

    fun onAddCategoryNameChange(name: String) {
        _categoriesState.update { it.copy(name = name) }
    }

    fun onEditCategoryNameChange(name: String) {
        _categoriesState.update {
            it.copy(
                editedCategory = Category(
                    id = categoriesState.value.editedCategory.id,
                    name = name,
                    description = categoriesState.value.editedCategory.description
                )
            )
        }
    }

    fun onAddCategoryDescriptionChange(description: String) {
        _categoriesState.update { it.copy(description = description) }
    }

    fun onEditCategoryDescriptionChange(description: String) {
        _categoriesState.update {
            it.copy(
                editedCategory = Category(
                    id = categoriesState.value.editedCategory.id,
                    name = categoriesState.value.editedCategory.name,
                    description = description
                )
            )
        }
    }

    fun onShowDialogAddNewCategory(isActive: Boolean) {
        _categoriesState.update { it.copy(showDialogAddNewCategory = isActive) }
    }

    fun onEditMode(isEditMode: Boolean) {
        _categoriesState.update { it.copy(isEditMode = isEditMode) }
    }

    fun onShowEditCategory(category: Category) {
        _categoriesState.update {
            it.copy(
                showDialogEditCategory = true,
                editedCategory = category
            )
        }
    }

    fun onHideDialogEditCategory() {
        _categoriesState.update { it.copy(showDialogEditCategory = false) }
    }

    fun onSaveEditedCategory() {
        if (categoriesState.value.name.length in 3..40) {
            viewModelScope.launch(Dispatchers.IO) {
                updateCategoryUseCase(categoriesState.value.editedCategory)
            }
            onHideDialogEditCategory()
            showErrorWrongName(false)
            clearTextfields()
        } else {
            showErrorWrongName(true)
        }
    }

    fun onDeleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCategoryUseCase(category)
        }
    }

    private fun clearTextfields() {
        _categoriesState.update {
            it.copy(
                name = "",
                description = ""
            )
        }
    }

    fun onCleanForm() {
        _categoriesState.update {
            it.copy(
                name = "",
                description = ""
            )
        }
    }
}
