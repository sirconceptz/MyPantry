package com.hermanowicz.pantry.domain.photo

import android.annotation.SuppressLint
import java.text.SimpleDateFormat
import java.util.Date
import javax.inject.Inject

class GetFileNameUseCase @Inject constructor() : (String) -> String {
    @SuppressLint("SimpleDateFormat")
    override fun invoke(extension: String): String {
        return SimpleDateFormat("yyyyMMdd_HHmmss").format(Date()) + "." + extension
    }
}
