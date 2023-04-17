package com.hermanowicz.mypantry.data.settings

import com.hermanowicz.mypantry.utils.enums.CameraMode
import com.hermanowicz.mypantry.utils.enums.DatabaseMode
import com.hermanowicz.mypantry.utils.enums.SizePrintedQRCodes

data class AppSettings(
    val databaseMode: String = DatabaseMode.LOCAL.name,
    val cameraMode: String = CameraMode.REAR.name,
    val scannerSound: Boolean = true,
    val sizePrintedQRCodes: String = SizePrintedQRCodes.BIG.name,
    val daysToNotifyBeforeExpiration: Float = 3f,
    val emailForNotifications: String = "",
    val pushNotifications: Boolean = true,
    val emailNotifications: Boolean = false
)
