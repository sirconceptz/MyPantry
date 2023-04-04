package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.di.repository.CategoriesRepository
import javax.inject.Inject

class UpdateCategoryUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : suspend (Category) -> Unit {
    override suspend fun invoke(category: Category) {
        categoriesRepository.update(category)
    }
}
