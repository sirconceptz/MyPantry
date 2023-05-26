package com.hermanowicz.pantry.navigation.features.myPantry.state

import com.hermanowicz.pantry.data.model.errorAlertSystem.ErrorAlert

data class ErrorAlertSystemState(
    var activeErrorList: List<ErrorAlert> = emptyList()
)
