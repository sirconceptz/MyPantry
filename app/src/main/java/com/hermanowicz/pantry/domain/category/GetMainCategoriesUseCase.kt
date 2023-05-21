package com.hermanowicz.pantry.domain.category

import com.hermanowicz.pantry.di.repository.CategoryRepository
import javax.inject.Inject

class GetMainCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : () -> Map<String, String> {
    override fun invoke(): Map<String, String> {
        return categoryRepository.getMainCategories()
    }
}
