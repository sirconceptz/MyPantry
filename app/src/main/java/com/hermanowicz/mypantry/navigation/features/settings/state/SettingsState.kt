package com.hermanowicz.mypantry.navigation.features.settings.state

import com.hermanowicz.mypantry.utils.enums.CameraValueType
import com.hermanowicz.mypantry.utils.enums.DatabaseMode
import com.hermanowicz.mypantry.utils.enums.QRCodeSizeValueType

data class SettingsState(
    val userEmailOrUnlogged: String = "Unlogged",
    val databaseMode: String = DatabaseMode.LOCAL.name,
    val showDatabaseModeDropdown: Boolean = false,
    val cameraToScanCodes: String = CameraValueType.REAR.name,
    val showCameraModeDropdown: Boolean = false,
    val scannerSound: Boolean = true,
    val sizeQrCodes: String = QRCodeSizeValueType.BIG.name,
    val daysToNotifyBeforeExpiration: Float = 3f,
    val emailAddressForNotifications: String = "None",
    val showSizeQrCodesDropdown: Boolean = false,
    val pushNotifications: Boolean = true,
    val showPushNotificationsDropdown: Boolean = false,
    val emailNotifications: Boolean = false,
    val showEmailNotificationsDropdown: Boolean = false,
    val showAuthorDialog: Boolean = false
)
