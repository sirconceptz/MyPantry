package com.hermanowicz.pantry.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "error"
)
data class ErrorEntity(
    @PrimaryKey
    val errorCode: Int = 0
)
