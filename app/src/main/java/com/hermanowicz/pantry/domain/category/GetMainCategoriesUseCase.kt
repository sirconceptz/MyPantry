package com.hermanowicz.pantry.domain.category

import android.content.Context
import com.hermanowicz.pantry.utils.category.MainCategories
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class GetMainCategoriesUseCase @Inject constructor(
    @ApplicationContext private val context: Context
) : () -> Map<String, String> {
    override fun invoke(): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        enumValues<MainCategories>().forEach { category ->
            map[category.name] = context.getString(category.nameResId)
        }
        return map
    }
}
