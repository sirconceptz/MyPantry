package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.di.repository.CategoriesRepository
import javax.inject.Inject

class GetMainCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : () -> Map<String, String> {
    override fun invoke(): Map<String, String> {
        return categoriesRepository.getMainCategories()
    }
}
