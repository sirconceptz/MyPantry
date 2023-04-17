package com.hermanowicz.mypantry.data.settings

import com.hermanowicz.mypantry.utils.enums.CameraValueType
import com.hermanowicz.mypantry.utils.enums.DatabaseMode
import com.hermanowicz.mypantry.utils.enums.QRCodeSizeValueType

data class AppSettings(
    val databaseMode: DatabaseMode = DatabaseMode.LOCAL,
    val cameraMode: CameraValueType = CameraValueType.REAR,
    val scannerSound: Boolean = true,
    val sizePrintedQRCodes: QRCodeSizeValueType = QRCodeSizeValueType.BIG,
    val daysToNotifyBeforeExpiration: Int = 3,
    val emailForNotifications: String = "",
    val pushNotifications: Boolean = true,
    val emailNotifications: Boolean = false
)
