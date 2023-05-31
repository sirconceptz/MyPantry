package com.hermanowicz.pantry.data.model

import com.hermanowicz.pantry.utils.enums.CameraMode
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import com.hermanowicz.pantry.utils.enums.QrCodeSize

data class AppSettings(
    val databaseMode: String = DatabaseMode.LOCAL.name,
    val cameraMode: String = CameraMode.REAR.name,
    val qrCodeSize: String = QrCodeSize.BIG.name,
    val daysToNotifyBeforeExpiration: Float = 3f,
    val emailForNotifications: String = "",
    val pushNotifications: Boolean = true,
    val pushNotificationsChanged: Boolean = false,
    val emailNotifications: Boolean = false
)
