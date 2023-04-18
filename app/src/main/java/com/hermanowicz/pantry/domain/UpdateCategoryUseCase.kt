package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoriesRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : suspend (Category) -> Unit {
    override suspend fun invoke(category: Category) {
        categoriesRepository.update(category)
    }
}