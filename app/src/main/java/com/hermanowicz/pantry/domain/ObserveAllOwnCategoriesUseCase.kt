package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllOwnCategoriesUseCase @Inject constructor(
    private val categoriesRepository: CategoriesRepository
) : () -> Flow<List<Category>> {
    override fun invoke(): Flow<List<Category>> {
        return categoriesRepository.observeAll().distinctUntilChanged()
    }
}
