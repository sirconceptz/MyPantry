package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoriesRepository
import javax.inject.Inject

class SaveCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoriesRepository
) : suspend (Category) -> Unit {
    override suspend fun invoke(category: Category) {
        categoryRepository.insert(category)
    }
}
