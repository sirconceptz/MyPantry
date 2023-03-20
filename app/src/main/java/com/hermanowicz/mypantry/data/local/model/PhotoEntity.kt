package com.hermanowicz.mypantry.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Photo")
data class PhotoEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String? = null,
    val content: String? = null
)
