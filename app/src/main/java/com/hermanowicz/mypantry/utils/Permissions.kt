package com.hermanowicz.mypantry.utils

import android.Manifest
import android.os.Build

object Permissions {
    val notificationPermissions =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            listOf(
                Manifest.permission.POST_NOTIFICATIONS
            )
        } else {
            emptyList()
        }

    val writePermissions = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
        listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.ACCESS_MEDIA_LOCATION
        )
    } else {
        listOf(
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }
}
