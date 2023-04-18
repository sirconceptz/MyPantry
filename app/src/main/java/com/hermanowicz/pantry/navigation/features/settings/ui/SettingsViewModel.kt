package com.hermanowicz.pantry.navigation.features.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.settings.AppSettings
import com.hermanowicz.pantry.domain.ClearDatabaseToFileUseCase
import com.hermanowicz.pantry.domain.FetchAppSettingsUseCase
import com.hermanowicz.pantry.domain.UpdateAppSettingsUseCase
import com.hermanowicz.pantry.domain.ValidateEmailUseCase
import com.hermanowicz.pantry.navigation.features.settings.state.SettingsState
import com.hermanowicz.pantry.utils.enums.EmailValidation
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
    private val clearDatabaseToFileUseCase: ClearDatabaseToFileUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase
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
                        emailNotificationsCheckboxEnabled = isEmailNotificationsCheckboxEnabled(
                            appSettings.emailForNotifications
                        )
                    )
                }
            }.collect()
        }
    }

    private fun isEmailNotificationsCheckboxEnabled(emailAddress: String): Boolean {
        return validateEmailUseCase(emailAddress) == EmailValidation.VALID
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
                emailNotifications = bool,
                showChangeNotificationsEmailDialog = false
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

    fun showEmailAddressDialog(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showChangeNotificationsEmailDialog = bool
            )
        }
    }

    fun onChangeEmailAddressForNotifications(emailAddress: String) {
        _settingsState.update {
            when (validateEmailUseCase(emailAddress)) {
                EmailValidation.VALID -> it.copy(
                    emailAddressForNotifications = emailAddress,
                    emailNotificationsCheckboxEnabled = true,
                    showChangeNotificationsEmailDialog = false
                )

                else -> it.copy(
                    emailAddressForNotifications = "",
                    emailNotificationsCheckboxEnabled = false,
                    emailNotifications = false,
                    showChangeNotificationsEmailDialog = false
                )
            }
        }
        updateAppSettings()
    }
}