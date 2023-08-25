package com.hermanowicz.pantry.navigation.features.ownCategories

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.domain.category.DeleteCategoryUseCase
import com.hermanowicz.pantry.domain.category.ObserveAllOwnCategoriesUseCase
import com.hermanowicz.pantry.domain.category.SaveCategoryUseCase
import com.hermanowicz.pantry.domain.category.UpdateCategoryUseCase
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.navigation.features.ownCategories.ui.OwnCategoriesViewModel
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@ExperimentalCoroutinesApi
class OwnCategoriesViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: OwnCategoriesViewModel
    private val mockObserveAllOwnCategoriesUseCase: ObserveAllOwnCategoriesUseCase = mock()
    private val mockSaveCategoryUseCase: SaveCategoryUseCase = mock()
    private val mockUpdateCategoryUseCase: UpdateCategoryUseCase = mock()
    private val mockDeleteCategoryUseCase: DeleteCategoryUseCase = mock()
    private val mockObserveDatabaseModeUseCase: ObserveDatabaseModeUseCase = mock()

    @Before
    fun setup() {
        viewModel = OwnCategoriesViewModel(
            mockObserveAllOwnCategoriesUseCase,
            mockSaveCategoryUseCase,
            mockUpdateCategoryUseCase,
            mockDeleteCategoryUseCase,
            mockObserveDatabaseModeUseCase
        )
    }

    @Test
    fun `onClickSaveCategory with valid data saves the category`() = runBlocking {
        viewModel.onAddCategoryNameChange("Category1")
        viewModel.onAddCategoryDescriptionChange("Description1")
        val expectedCategory = Category(id = 0, name = "Category1", description = "Description1")
        viewModel.onClickSaveCategory()
        delay(100)
        verify(mockSaveCategoryUseCase).invoke(expectedCategory)
        assertEquals(false, viewModel.categoriesState.value.showErrorWrongName)
        assertEquals("", viewModel.categoriesState.value.name)
        assertEquals("", viewModel.categoriesState.value.description)
    }

    @Test
    fun `onClickSaveCategory with invalid data shows error`() {
        viewModel.onAddCategoryNameChange("C")
        viewModel.onClickSaveCategory()

        assertEquals(true, viewModel.categoriesState.value.showErrorWrongName)
    }

    @Test
    fun `onAddCategoryNameChange updates name`() {
        viewModel.onAddCategoryNameChange("NewCategory")
        assertEquals("NewCategory", viewModel.categoriesState.value.name)
    }

    @Test
    fun `onAddCategoryDescriptionChange updates description`() {
        viewModel.onAddCategoryDescriptionChange("NewDescription")
        assertEquals("NewDescription", viewModel.categoriesState.value.description)
    }

    @Test
    fun `onShowDialogAddNewCategory updates showDialogAddNewCategory`() {
        viewModel.onShowDialogAddNewCategory(true)
        assertEquals(true, viewModel.categoriesState.value.showDialogAddNewCategory)
    }

    @Test
    fun `onEditMode updates isEditMode`() {
        viewModel.onEditMode(true)
        assertEquals(true, viewModel.categoriesState.value.isEditMode)
    }

    @Test
    fun `onShowEditCategory updates showDialogEditCategory`() {
        val category = Category(id = 1, name = "Category1", description = "Description1")
        viewModel.onShowEditCategory(category)

        assertEquals(true, viewModel.categoriesState.value.showDialogEditCategory)
        assertEquals(category, viewModel.categoriesState.value.editedCategory)
    }
}
