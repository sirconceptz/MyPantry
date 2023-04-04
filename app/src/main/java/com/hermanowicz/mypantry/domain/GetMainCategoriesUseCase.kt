package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.di.repository.CategoriesRepository
import javax.inject.Inject

class GetMainCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : () -> Map<String, Int> {
    override fun invoke(): Map<String, Int> {
        return categoriesRepository.getMainCategories()
    }
}
