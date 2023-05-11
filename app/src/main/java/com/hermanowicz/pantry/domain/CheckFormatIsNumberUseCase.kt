package com.hermanowicz.pantry.domain

import javax.inject.Inject

class CheckFormatIsNumberUseCase @Inject constructor() : (String) -> Boolean {
    private val numberPattern = Regex("^\\d+\$")

    override fun invoke(text: String): Boolean {
        return text.matches(numberPattern)
    }
}