package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import javax.inject.Inject

class DeleteCategoryUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : suspend (Category) -> Unit {
    override suspend fun invoke(category: Category) {
        categoryRepository.delete(category)
    }
}
