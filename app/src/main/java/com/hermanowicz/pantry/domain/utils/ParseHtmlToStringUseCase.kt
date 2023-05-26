package com.hermanowicz.pantry.domain.utils

import android.text.Html
import javax.inject.Inject

class ParseHtmlToStringUseCase @Inject constructor() : (String) -> String {
    override fun invoke(htmlString: String): String {
        return Html.fromHtml(htmlString, Html.FROM_HTML_MODE_LEGACY).toString()
    }
}
