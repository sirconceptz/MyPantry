package com.hermanowicz.pantry.navigation.features.settings.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hermanowicz.pantry.data.model.AppSettings
import com.hermanowicz.pantry.domain.account.DeleteUserAccountUseCase
import com.hermanowicz.pantry.domain.settings.ClearDatabaseUseCase
import com.hermanowicz.pantry.domain.settings.ExportDatabaseToCloudUseCase
import com.hermanowicz.pantry.domain.settings.FetchUserEmailOrUnloggedUseCase
import com.hermanowicz.pantry.domain.settings.ObserveAppSettingsUseCase
import com.hermanowicz.pantry.domain.settings.ReCreateNotificationsForAllProductsUseCase
import com.hermanowicz.pantry.domain.settings.UpdateAppSettingsUseCase
import com.hermanowicz.pantry.domain.settings.ValidateEmailUseCase
import com.hermanowicz.pantry.domain.utils.CheckIsUserLoggedUseCase
import com.hermanowicz.pantry.navigation.features.settings.state.SettingsState
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.EmailValidation
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val observeAppSettingsUseCase: ObserveAppSettingsUseCase,
    private val updateAppSettingsUseCase: UpdateAppSettingsUseCase,
    private val clearDatabaseUseCase: ClearDatabaseUseCase,
    private val validateEmailUseCase: ValidateEmailUseCase,
    private val exportDatabaseToCloudUseCase: ExportDatabaseToCloudUseCase,
    private val fetchUserEmailOrUnloggedUseCase: FetchUserEmailOrUnloggedUseCase,
    private val deleteUserAccountUseCase: DeleteUserAccountUseCase,
    private val checkIsUserLoggedUseCase: CheckIsUserLoggedUseCase,
    private val reCreateNotificationsForAllProductsUseCase: ReCreateNotificationsForAllProductsUseCase
) : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsState())
    var settingsState: StateFlow<SettingsState> = _settingsState.asStateFlow()

    init {
        observeAppSettings()
    }

    fun observeAppSettings() {
        viewModelScope.launch {
            observeAppSettingsUseCase().collect { appSettings ->
                _settingsState.update {
                    it.copy(
                        databaseMode = appSettings.databaseMode,
                        cameraToScanCodes = appSettings.cameraMode,
                        sizeQrCodes = appSettings.qrCodeSize,
                        daysToNotifyBeforeExpiration = appSettings.daysToNotifyBeforeExpiration,
                        userEmailOrUnlogged = fetchUserEmailOrUnloggedUseCase(),
                        emailNotifications = appSettings.emailNotifications,
                        emailAddressForNotifications = appSettings.emailForNotifications,
                        pushNotifications = appSettings.pushNotifications,
                        emailNotificationsCheckboxEnabled = isEmailNotificationsCheckboxEnabled(
                            appSettings.emailForNotifications
                        ),
                        isUserLogged = checkIsUserLoggedUseCase()
                    )
                }
                disableOnlineFeaturesIfUnlogged()
            }
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
        recreateNotifications(databaseMode)
        updateAppSettings()
    }

    private fun recreateNotifications(databaseMode: String) {
        val mode = enumValueOf<DatabaseMode>(databaseMode)
        viewModelScope.launch(Dispatchers.IO) {
            if (mode == DatabaseMode.LOCAL) {
                reCreateNotificationsForAllProductsUseCase(DatabaseMode.ONLINE, DatabaseMode.LOCAL)
            } else {
                reCreateNotificationsForAllProductsUseCase(DatabaseMode.LOCAL, DatabaseMode.ONLINE)
            }
        }
    }

    fun updateAppSettings() {
        val appSettings = AppSettings(
            databaseMode = settingsState.value.databaseMode,
            cameraMode = settingsState.value.cameraToScanCodes,
            qrCodeSize = settingsState.value.sizeQrCodes,
            daysToNotifyBeforeExpiration = settingsState.value.daysToNotifyBeforeExpiration,
            emailForNotifications = settingsState.value.emailAddressForNotifications,
            pushNotifications = settingsState.value.pushNotifications,
            emailNotifications = settingsState.value.emailNotifications,
            pushNotificationsChanged = settingsState.value.pushNotificationsStateChanged
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

    fun onChangeDaysToNotifyBeforeExpiration(days: Float) {
        _settingsState.update {
            it.copy(
                daysToNotifyBeforeExpiration = days
            )
        }
        updateAppSettings()
    }

    fun onChangePushNotificationsEnabled(bool: Boolean) {
        _settingsState.update {
            it.copy(
                pushNotifications = bool,
                pushNotificationsStateChanged = true
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

    fun onChangeNotificationsStateChanged(bool: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            delay(3000)
            _settingsState.update {
                it.copy(
                    pushNotificationsStateChanged = bool
                )
            }
            updateAppSettings()
        }
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
            clearDatabaseUseCase()
        }
        showClearDatabaseDialog(false)
    }

    fun showEmailAddressDialog(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showChangeNotificationsEmailDialog = bool,
                tempEmailAddressForNotifications = settingsState.value.emailAddressForNotifications
            )
        }
    }

    fun showExportDatabaseToCloudDialog(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showExportDatabaseToCloudDialog = bool
            )
        }
    }

    fun showSignInOrSignOut(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showSignInForm = bool
            )
        }
    }

    fun onChangeEmailAddressForNotifications() {
        val emailAddress = settingsState.value.tempEmailAddressForNotifications
        val parsedAddress = emailAddress.replace("\\s".toRegex(), "")

        _settingsState.update {
            when (validateEmailUseCase(parsedAddress)) {
                EmailValidation.VALID -> it.copy(
                    emailAddressForNotifications = parsedAddress,
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

    fun onConfirmExportDatabaseToCloud() {
        viewModelScope.launch(Dispatchers.IO) {
            exportDatabaseToCloudUseCase()
        }
        showExportDatabaseToCloudDialog(false)
    }

    fun showUserEmail() {
        _settingsState.update {
            it.copy(
                userEmailOrUnlogged = fetchUserEmailOrUnloggedUseCase(),
                isUserLogged = checkIsUserLoggedUseCase()
            )
        }
        disableOnlineFeaturesIfUnlogged()
    }

    fun showDeleteAccountDialog(bool: Boolean) {
        _settingsState.update {
            it.copy(
                showDeleteAccountDialog = bool
            )
        }
    }

    private fun enableDatabaseModeDropdown(bool: Boolean) {
        _settingsState.update {
            it.copy(
                databaseModeDropdownEnabled = bool
            )
        }
    }

    private fun enableExportToCloud(bool: Boolean) {
        _settingsState.update {
            it.copy(
                exportDatabaseToCloudEnabled = bool
            )
        }
    }

    fun onConfirmDeleteAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            deleteUserAccountUseCase()
        }
        showDeleteAccountDialog(false)
    }

    private fun disableOnlineFeaturesIfUnlogged() {
        val isUserLogged = checkIsUserLoggedUseCase()
        if (!isUserLogged) {
            onChangeDatabaseMode(DatabaseMode.LOCAL.name)
        }
        enableDatabaseModeDropdown(isUserLogged)
        enableExportToCloud(isUserLogged)
    }

    fun onTempEmailAddressForNotificationsChange(emailAddress: String) {
        _settingsState.update {
            it.copy(
                tempEmailAddressForNotifications = emailAddress
            )
        }
    }
}
