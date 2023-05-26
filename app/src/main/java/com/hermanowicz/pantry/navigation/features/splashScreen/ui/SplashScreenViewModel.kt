package com.hermanowicz.pantry.navigation.features.splashScreen.ui

import androidx.lifecycle.ViewModel
import com.hermanowicz.pantry.navigation.features.splashScreen.state.SplashScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor() : ViewModel() {
    private val _uiState = MutableStateFlow(SplashScreenState())
    val uiState: StateFlow<SplashScreenState> = _uiState

    fun onNavigateToMyPantry(bool: Boolean) {
        _uiState.update {
            it.copy(onNavigateToMyPantry = bool)
        }
    }
}
