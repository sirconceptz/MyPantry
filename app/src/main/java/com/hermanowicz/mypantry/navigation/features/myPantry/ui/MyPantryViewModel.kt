package com.hermanowicz.mypantry.navigation.features.myPantry.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.di.repository.ProductRepository
import com.hermanowicz.mypantry.domain.GetGroupProductListUseCase
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryModel
import com.hermanowicz.mypantry.navigation.features.myPantry.state.MyPantryUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyPantryViewModel @Inject constructor(
    val getGroupProductListUseCase: GetGroupProductListUseCase,
    val productRepository: ProductRepository
) : ViewModel() {
    private val _uiState = MutableStateFlow<MyPantryUiState>(MyPantryUiState.Empty)
    val uiState: StateFlow<MyPantryUiState> = _uiState

    init {
        fetchProducts()
    }

    private fun fetchProducts() {
        _uiState.value = MyPantryUiState.Loading
        viewModelScope.launch(Dispatchers.IO) {
            try {
                productRepository.observeAll().collect { products ->
                    _uiState.value = MyPantryUiState.Loaded(
                        MyPantryModel(
                            products
                        )
                    )
                }
            } catch (e: Exception) {
                _uiState.value = MyPantryUiState.Error(e.toString())
            }
        }
    }
}
