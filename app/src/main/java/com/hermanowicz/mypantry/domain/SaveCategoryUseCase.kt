package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.di.repository.CategoriesRepository
import javax.inject.Inject

class SaveCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoriesRepository
) : suspend (Category) -> Unit {
    override suspend fun invoke(category: Category) {
        categoryRepository.insert(category)
    }
}
