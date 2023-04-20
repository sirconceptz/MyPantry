package com.hermanowicz.pantry.navigation.features.settings.state

import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.SizePrintedQRCodes

data class SettingsState(
    val userEmailOrUnlogged: String = "",
    val databaseMode: String = DatabaseMode.LOCAL.name,
    val showDatabaseModeDropdown: Boolean = false,
    val cameraToScanCodes: String = CameraMode.REAR.name,
    val showCameraModeDropdown: Boolean = false,
    val scannerSound: Boolean = true,
    val sizeQrCodes: String = SizePrintedQRCodes.BIG.name,
    val daysToNotifyBeforeExpiration: Float = 3f,
    val emailAddressForNotifications: String = "",
    val showSizeQrCodesDropdown: Boolean = false,
    val pushNotifications: Boolean = true,
    val showPushNotificationsDropdown: Boolean = false,
    val emailNotifications: Boolean = false,
    val emailNotificationsCheckboxEnabled: Boolean = false,
    val showEmailNotificationsDropdown: Boolean = false,
    val showAuthorDialog: Boolean = false,
    val showClearDatabaseDialog: Boolean = false,
    val showChangeNotificationsEmailDialog: Boolean = false,
    val showExportDatabaseToCloudDialog: Boolean = false,
    val showSignInForm: Boolean = false,
    val reObserveDatabase: Boolean = false,
    val isUserLogged: Boolean = false,
    val showDeleteAccountDialog: Boolean = false
)
