package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.di.repository.CategoriesRepository
import javax.inject.Inject

class GetOwnCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : () -> List<Category> {
    override fun invoke(): List<Category> {
        return categoriesRepository.getOwnCategories()
    }
}
