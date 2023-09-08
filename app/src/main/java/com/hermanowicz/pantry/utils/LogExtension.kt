package com.hermanowicz.pantry.utils

import timber.log.Timber

fun Any?.printToLog(tag: String = "DEBUG_LOG") {
    Timber.d(tag, toString())
}
