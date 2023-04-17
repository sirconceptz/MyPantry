package com.hermanowicz.mypantry.navigation.features.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.data.repository.SettingsRepository
import com.hermanowicz.mypantry.navigation.features.settings.state.SettingsState
import com.hermanowicz.mypantry.utils.enums.DatabaseMode
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val settingsRepository: SettingsRepository
) : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    var settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    init {
        viewModelScope.launch {
            settingsRepository.databaseMode.map { mode ->
                _settingsState.update {
                    it.copy(
                        databaseMode = mode ?: DatabaseMode.LOCAL.name
                    )
                }
            }
            settingsRepository.scannerSoundMode.map { mode ->
                _settingsState.update {
                    it.copy(
                        scannerSound = mode ?: true
                    )
                }
            }
        }
    }

    fun onChangeDatabaseMode(databaseMode: String) {
        viewModelScope.launch {
            settingsRepository.updateDatabaseMode(databaseMode)
        }
        _settingsState.update {
            it.copy(
                databaseMode = databaseMode,
                showDatabaseModeDropdown = false
            )
        }
    }

    fun showDatabaseMode(bool: Boolean) {
        _settingsState.update {
            it.copy(showDatabaseModeDropdown = bool)
        }
    }

    fun onChangeCameraMode(cameraMode: String) {
        _settingsState.update {
            it.copy(
                cameraToScanCodes = cameraMode,
                showCameraModeDropdown = false
            )
        }
    }

    fun showCameraMode(bool: Boolean) {
        _settingsState.update {
            it.copy(showCameraModeDropdown = bool)
        }
    }

    fun onChangeQrCodeSizeMode(sizeQrCode: String) {
        _settingsState.update {
            it.copy(
                sizeQrCodes = sizeQrCode,
                showSizeQrCodesDropdown = false
            )
        }
    }

    fun showQrCodeSizeMode(bool: Boolean) {
        _settingsState.update {
            it.copy(showSizeQrCodesDropdown = bool)
        }
    }

    fun onChangeScannerSoundMode(scannerSound: Boolean) {
        viewModelScope.launch {
            settingsRepository.updateScannerSoundMode(scannerSound)
        }
        _settingsState.update {
            it.copy(
                scannerSound = scannerSound
            )
        }
    }

    fun onChangeDaysToNotifyBeforeExpiration(days: Float) {
        _settingsState.update {
            it.copy(
                daysToNotifyBeforeExpiration = days
            )
        }
    }

    fun onChangePushNotifications(bool: Boolean) {
        _settingsState.update {
            it.copy(
                pushNotifications = bool
            )
        }
    }

    fun onChangeEmailNotifications(bool: Boolean) {
        _settingsState.update {
            it.copy(
                emailNotifications = bool
            )
        }
    }

    fun showAuthorDialog(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showAuthorDialog = bool
            )
        }
    }
}
