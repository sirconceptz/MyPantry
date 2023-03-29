package com.hermanowicz.mypantry.domain

import com.hermanowicz.mypantry.data.model.Category
import com.hermanowicz.mypantry.di.repository.CategoriesRepository
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
