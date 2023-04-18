package com.hermanowicz.pantry.domain

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllOwnCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : () -> Flow<List<Category>> {
    override fun invoke(): Flow<List<Category>> {
        return categoryRepository.observeAll().distinctUntilChanged()
    }
}
