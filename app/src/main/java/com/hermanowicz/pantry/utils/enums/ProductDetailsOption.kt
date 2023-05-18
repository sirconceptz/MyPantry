package com.hermanowicz.pantry.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R

enum class ProductDetailsOption(val nameResId: Int) {
    PRINT_QR_CODES(R.string.print_qr_codes),
    ADD_PHOTO(R.string.add_photo),
    ADD_BARCODE(R.string.add_barcode),
    EDIT(R.string.edit),
    DELETE(R.string.delete);

    companion object {
        @Composable
        fun toPairList(): List<Pair<String, String>> {
            val pairList: MutableList<Pair<String, String>> = mutableListOf()
            enumValues<ProductDetailsOption>().forEach { option ->
                pairList.add(Pair(option.name, stringResource(option.nameResId)))
            }
            return pairList
        }
    }
}
