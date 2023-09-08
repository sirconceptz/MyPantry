package com.hermanowicz.pantry.domain.product.utils

import android.annotation.SuppressLint
import java.text.SimpleDateFormat

class IsDateAfter {
    companion object {
        @SuppressLint("SimpleDateFormat")
        fun isDateAfter(productDateString: String, filterProductDateString: String): Boolean {
            var isDateAfter = true
            val sdf = SimpleDateFormat("yyyy-MM-dd")
            if (productDateString.isNotEmpty() && filterProductDateString.isNotEmpty()) {
                val productDate = sdf.parse(productDateString)
                val filterProductDate = sdf.parse(filterProductDateString)
                if (productDate != null) {
                    isDateAfter = productDate.after(filterProductDate)
                }
            }
            return isDateAfter
        }
    }
}
