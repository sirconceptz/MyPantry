package com.hermanowicz.pantry.data.model.errorAlertSystem

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WPError(
    @SerializedName("title")
    @Expose
    val title: WPTitle? = null,

    @SerializedName("date")
    @Expose
    val date: String? = null,

    @SerializedName("content")
    @Expose
    val content: WPContent? = null
)