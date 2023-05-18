package com.hermanowicz.pantry.data.repository

import android.content.Context
import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.local.dataSource.CategoryLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.CategoryRemoteDataSource
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.domain.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.utils.category.MainCategories
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase,
    @ApplicationContext private val context: Context
) : CategoryRepository {
    override fun observeById(id: Int, databaseMode: DatabaseMode): Flow<Category> {
        return if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.observeById(id).filterNotNull().map { categoryEntity ->
                categoryEntity.toDomainModel()
            }
        } else {
            remoteDataSource.observeById(id).filterNotNull().map { categoryEntity ->
                categoryEntity.toDomainModel()
            }
        }
    }

    override fun observeAll(databaseMode: DatabaseMode): Flow<List<Category>> {
        return if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.observeAll().map { categoryEntities ->
                categoryEntities.map { categoryEntity -> categoryEntity.toDomainModel() }
            }
        } else {
            remoteDataSource.observeAll().map { categoryEntities ->
                categoryEntities.map { categoryEntity -> categoryEntity.toDomainModel() }
            }
        }
    }

    override fun getAllLocal(): List<Category> {
        return localDataSource.getAll().map { it.toDomainModel() }
    }

    override fun getMainCategories(): Map<String, String> {
        val map: MutableMap<String, String> = mutableMapOf()
        enumValues<MainCategories>().forEach { category ->
            map[category.name] = context.getString(category.nameResId)
        }
        return map
    }

    override suspend fun getLastId(databaseMode: DatabaseMode): Int {
        return observeAll(databaseMode).map { categories ->
            categories.maxOf { it.id }
        }.first()
    }

    override suspend fun insert(category: Category) {
        if (observeDatabaseModeUseCase().first() == DatabaseMode.LOCAL)
            localDataSource.insert(category.toEntityModel())
        else {
            val id = getLastId(DatabaseMode.ONLINE) + 1
            remoteDataSource.insert(category.copy(id = id).toEntityModel())
        }
    }

    override suspend fun insertRemote(category: Category) {
        remoteDataSource.insert(category.toEntityModel())
    }

    override suspend fun update(category: Category) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.update(category.toEntityModel())
        else
            remoteDataSource.update(category.toEntityModel())
    }

    override suspend fun delete(category: Category) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.delete(category.toEntityModel())
        else
            remoteDataSource.delete(category.toEntityModel())
    }

    override suspend fun deleteAllCurrentDatabase() {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL)
            localDataSource.deleteAll()
        else
            remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllRemote() {
        remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllLocal() {
        localDataSource.deleteAll()
    }
}
