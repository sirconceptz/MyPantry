package com.hermanowicz.pantry.domain.category

import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import javax.inject.Inject

class ObserveAllOwnCategoriesUseCase @Inject constructor(
    private val categoryRepository: CategoryRepository
) : (DatabaseMode) -> Flow<List<Category>> {
    override fun invoke(databaseMode: DatabaseMode): Flow<List<Category>> {
        return categoryRepository.observeAll(databaseMode).distinctUntilChanged()
    }
}
