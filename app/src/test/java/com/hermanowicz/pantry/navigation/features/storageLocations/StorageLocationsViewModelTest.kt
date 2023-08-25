package com.hermanowicz.pantry.navigation.features.storageLocations

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hermanowicz.pantry.data.model.StorageLocation
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.domain.storageLocation.DeleteStorageLocationUseCase
import com.hermanowicz.pantry.domain.storageLocation.ObserveAllStorageLocationsUseCase
import com.hermanowicz.pantry.domain.storageLocation.SaveStorageLocationsUseCase
import com.hermanowicz.pantry.domain.storageLocation.UpdateStorageLocationUseCase
import com.hermanowicz.pantry.navigation.features.storageLocations.ui.StorageLocationsViewModel
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
class StorageLocationsViewModelTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var viewModel: StorageLocationsViewModel
    private val mockObserveAllStorageLocationsUseCase: ObserveAllStorageLocationsUseCase = mock()
    private val mockSaveStorageLocationsUseCase: SaveStorageLocationsUseCase = mock()
    private val mockUpdateStorageLocationUseCase: UpdateStorageLocationUseCase = mock()
    private val mockDeleteStorageLocationUseCase: DeleteStorageLocationUseCase = mock()
    private val mockObserveDatabaseModeUseCase: ObserveDatabaseModeUseCase = mock()

    @Before
    fun setup() {
        viewModel = StorageLocationsViewModel(
            mockObserveAllStorageLocationsUseCase,
            mockSaveStorageLocationsUseCase,
            mockDeleteStorageLocationUseCase,
            mockUpdateStorageLocationUseCase,
            mockObserveDatabaseModeUseCase
        )
    }

    @Test
    fun `onClickSaveStorageLocation with valid data saves the storage location`() = runBlocking {
        viewModel.onNameChange("StorageLocation1")
        viewModel.onDescriptionChange("Description1")
        val expectedStorageLocation = StorageLocation(id = 0, name = "StorageLocation1", description = "Description1")
        viewModel.onClickSaveStorageLocation()
        delay(100)
        verify(mockSaveStorageLocationsUseCase).invoke(expectedStorageLocation)
        assertEquals(false, viewModel.storageLocationState.value.showErrorWrongName)
        assertEquals("", viewModel.storageLocationState.value.name)
        assertEquals("", viewModel.storageLocationState.value.description)
    }

    @Test
    fun `onClickSaveStorageLocation with invalid data shows error`() {
        viewModel.onNameChange("S")
        viewModel.onClickSaveStorageLocation()

        assertEquals(true, viewModel.storageLocationState.value.showErrorWrongName)
    }

    @Test
    fun `onAddStorageLocationNameChange updates name`() {
        viewModel.onNameChange("NewStorageLocation")
        assertEquals("NewStorageLocation", viewModel.storageLocationState.value.name)
    }

    @Test
    fun `onAddCategoryDescriptionChange updates description`() {
        viewModel.onDescriptionChange("NewDescription")
        assertEquals("NewDescription", viewModel.storageLocationState.value.description)
    }

    @Test
    fun `onShowDialogAddNewCategory updates showDialogAddNewCategory`() {
        viewModel.onShowDialogAddNewStorageLocation(true)
        assertEquals(true, viewModel.storageLocationState.value.showDialogAddNewStorageLocation)
    }

    @Test
    fun `onEditMode updates isEditMode`() {
        viewModel.onEditMode(true)
        assertEquals(true, viewModel.storageLocationState.value.isEditMode)
    }

    @Test
    fun `onShowEditCategory updates showDialogEditCategory`() {
        val storageLocation = StorageLocation(id = 1, name = "StorageLocation1", description = "Description1")
        viewModel.onShowEditStorageLocation(storageLocation)

        assertEquals(true, viewModel.storageLocationState.value.showDialogEditStorageLocation)
        assertEquals(storageLocation, viewModel.storageLocationState.value.editedStorageLocation)
    }
}
