package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import javax.inject.Inject

class GetOwnCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : () -> List<Category> {
    override fun invoke(): List<Category> {
        return categoryRepository.getOwnCategories()
    }
}
