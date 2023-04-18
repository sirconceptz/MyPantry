package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoriesRepository
import javax.inject.Inject

class GetOwnCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : () -> List<Category> {
    override fun invoke(): List<Category> {
        return categoriesRepository.getOwnCategories()
    }
}
