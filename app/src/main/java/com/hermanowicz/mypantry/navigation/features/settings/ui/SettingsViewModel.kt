package com.hermanowicz.mypantry.navigation.features.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.mypantry.data.settings.AppSettings
import com.hermanowicz.mypantry.domain.ClearDatabaseToFileUseCase
import com.hermanowicz.mypantry.domain.FetchAppSettingsUseCase
import com.hermanowicz.mypantry.domain.UpdateAppSettingsUseCase
import com.hermanowicz.mypantry.navigation.features.settings.state.SettingsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val fetchAppSettingsUseCase: FetchAppSettingsUseCase,
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase,
    private val clearDatabaseToFileUseCase: ClearDatabaseToFileUseCase
) : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    var settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    init {
        viewModelScope.launch {
            fetchAppSettingsUseCase().map { appSettings ->
                _settingsState.update {
                    it.copy(
                        databaseMode = appSettings.databaseMode,
                        scannerSound = appSettings.scannerSound,
                        cameraToScanCodes = appSettings.cameraMode,
                        sizeQrCodes = appSettings.sizePrintedQRCodes,
                        daysToNotifyBeforeExpiration = appSettings.daysToNotifyBeforeExpiration,
                        emailNotifications = appSettings.emailNotifications,
                        emailAddressForNotifications = appSettings.emailForNotifications,
                        pushNotifications = appSettings.pushNotifications,
                    )
                }
            }.collect()
        }
    }

    fun onChangeDatabaseMode(databaseMode: String) {
        _settingsState.update {
            it.copy(
                databaseMode = databaseMode,
                showDatabaseModeDropdown = false
            )
        }
        updateAppSettings()
    }

    private fun updateAppSettings() {
        val appSettings = AppSettings(
            databaseMode = settingsState.value.databaseMode,
            cameraMode = settingsState.value.cameraToScanCodes,
            scannerSound = settingsState.value.scannerSound,
            sizePrintedQRCodes = settingsState.value.sizeQrCodes,
            daysToNotifyBeforeExpiration = settingsState.value.daysToNotifyBeforeExpiration,
            emailForNotifications = settingsState.value.emailAddressForNotifications,
            pushNotifications = settingsState.value.pushNotifications,
            emailNotifications = settingsState.value.emailNotifications
        )
        viewModelScope.launch(Dispatchers.IO) {
            updateAppSettingsUseCase(appSettings)
        }
    }

    fun showDatabaseMode(bool: Boolean) {
        _settingsState.update {
            it.copy(showDatabaseModeDropdown = bool)
        }
        updateAppSettings()
    }

    fun onChangeCameraMode(cameraMode: String) {
        _settingsState.update {
            it.copy(
                cameraToScanCodes = cameraMode,
                showCameraModeDropdown = false
            )
        }
        updateAppSettings()
    }

    fun showCameraMode(bool: Boolean) {
        _settingsState.update {
            it.copy(showCameraModeDropdown = bool)
        }
        updateAppSettings()
    }

    fun onChangeQrCodeSizeMode(sizeQrCode: String) {
        _settingsState.update {
            it.copy(
                sizeQrCodes = sizeQrCode,
                showSizeQrCodesDropdown = false
            )
        }
        updateAppSettings()
    }

    fun showQrCodeSizeMode(bool: Boolean) {
        _settingsState.update {
            it.copy(showSizeQrCodesDropdown = bool)
        }
        updateAppSettings()
    }

    fun onChangeScannerSoundMode(scannerSound: Boolean) {
        _settingsState.update {
            it.copy(
                scannerSound = scannerSound
            )
        }
        updateAppSettings()
    }

    fun onChangeDaysToNotifyBeforeExpiration(days: Float) {
        _settingsState.update {
            it.copy(
                daysToNotifyBeforeExpiration = days
            )
        }
        updateAppSettings()
    }

    fun onChangePushNotifications(bool: Boolean) {
        _settingsState.update {
            it.copy(
                pushNotifications = bool
            )
        }
        updateAppSettings()
    }

    fun onChangeEmailNotifications(bool: Boolean) {
        _settingsState.update {
            it.copy(
                emailNotifications = bool
            )
        }
        updateAppSettings()
    }

    fun showAuthorDialog(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showAuthorDialog = bool
            )
        }
    }

    fun showClearDatabaseDialog(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showClearDatabaseDialog = bool
            )
        }
    }

    fun onConfirmClearDatabase() {
        viewModelScope.launch(Dispatchers.IO) {
            clearDatabaseToFileUseCase()
        }
        showClearDatabaseDialog(false)
    }
}
