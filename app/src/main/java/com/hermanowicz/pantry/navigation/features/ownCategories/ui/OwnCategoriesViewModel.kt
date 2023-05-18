package com.hermanowicz.pantry.navigation.features.ownCategories.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.domain.DeleteCategoryUseCase
import com.hermanowicz.pantry.domain.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.SaveCategoryUseCase
import com.hermanowicz.pantry.domain.UpdateCategoryUseCase
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesModel
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesState
import com.hermanowicz.pantry.navigation.features.ownCategories.state.CategoriesUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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
    private val _uiState = MutableStateFlow<CategoriesUiState>(CategoriesUiState.Empty)
    val uiState: StateFlow<CategoriesUiState> = _uiState

    private val _categoriesState = MutableStateFlow(CategoriesState())
    var categoriesState: StateFlow<CategoriesState> =
        _categoriesState.asStateFlow()

    init {
        observeCategories()
    }

    fun observeCategories() {
        _uiState.value = CategoriesUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                observeDatabaseModeUseCase().collect { databaseMode ->
                    observeAllOwnCategoriesUseCase(databaseMode).collect {
                        _uiState.value = CategoriesUiState.Loaded(
                            CategoriesModel(
                                categories = it,
                                loadingVisible = false
                            )
                        )
                    }
                }
            } catch (e: Exception) {
                _uiState.value = CategoriesUiState.Error(e.toString())
            }
        }
    }

    fun onClickSaveCategory() {
        val category = Category(
            name = categoriesState.value.name,
            description = categoriesState.value.description
        )
        viewModelScope.launch(Dispatchers.IO) {
            saveCategoryUseCase(category)
        }
        _categoriesState.update { it.copy(showDialogAddNewCategory = false) }
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
        viewModelScope.launch(Dispatchers.IO) {
            updateCategoryUseCase(categoriesState.value.editedCategory)
        }
        onHideDialogEditCategory()
    }

    fun onDeleteCategory(category: Category) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteCategoryUseCase(category)
        }
    }
}
