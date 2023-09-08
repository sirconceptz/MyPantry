package com.hermanowicz.pantry.domain.utils

import com.hermanowicz.pantry.utils.RegexFormats
import javax.inject.Inject

class CheckFormatIsNumberUseCase @Inject constructor() : (String) -> Boolean {

    override fun invoke(text: String): Boolean {
        return text.matches(RegexFormats.NUMBER.regex) || text.isEmpty()
    }
}

class Solution {
    fun lengthOfLastWord(s: String): Int {
        val reversed = s.trim().reversed()
        val index = reversed.indexOfFirst { it == ' ' }
        return if (index >= 0) {
            index
        } else {
            reversed.length
        }
    }
}
