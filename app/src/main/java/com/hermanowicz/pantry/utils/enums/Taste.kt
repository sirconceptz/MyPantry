package com.hermanowicz.pantry.utils.enums

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import com.hermanowicz.pantry.R

enum class Taste(val nameResId: Int) {
    SWEET(R.string.sweet),
    SOUR(R.string.sour),
    SWEET_AND_SOUR(R.string.sweet_and_sour),
    SALTY(R.string.salty),
    SPICY(R.string.spicy);

    companion object {
        @Composable
        fun toPairList(): List<Pair<String, String>> {
            val pairList: MutableList<Pair<String, String>> = mutableListOf()
            enumValues<Taste>().forEach { taste ->
                pairList.add(Pair(taste.name, stringResource(taste.nameResId)))
            }
            return pairList
        }
    }
}
