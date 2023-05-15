package com.hermanowicz.pantry.data.model.errorAlertSystem

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class WPContent(
    @SerializedName("rendered")
    @Expose
    var rendered: String? = null,

    @SerializedName("protected")
    @Expose
    var protected: Boolean? = null
)