package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.data.mapper.toEntityModel
import com.hermanowicz.pantry.data.model.Category
import com.hermanowicz.pantry.di.local.dataSource.CategoryLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.CategoryRemoteDataSource
import com.hermanowicz.pantry.di.repository.CategoryRepository
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepositoryImpl @Inject constructor(
    private val localDataSource: CategoryLocalDataSource,
    private val remoteDataSource: CategoryRemoteDataSource,
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase
) : CategoryRepository {
    override fun observeById(id: Int, databaseMode: DatabaseMode): Flow<Category> {
        return if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.observeById(id).filterNotNull().map { categoryEntity ->
                categoryEntity.toDomainModel()
            }.distinctUntilChanged()
        } else {
            remoteDataSource.observeById(id).filterNotNull().map { categoryEntity ->
                categoryEntity.toDomainModel()
            }.distinctUntilChanged()
        }
    }

    override fun observeAll(databaseMode: DatabaseMode): Flow<List<Category>> {
        return if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.observeAll().map { categoryEntities ->
                categoryEntities.map { categoryEntity -> categoryEntity.toDomainModel() }
            }.distinctUntilChanged()
        } else {
            remoteDataSource.observeAll().map { categoryEntities ->
                categoryEntities.map { categoryEntity -> categoryEntity.toDomainModel() }
            }.distinctUntilChanged()
        }
    }

    override suspend fun getLastId(databaseMode: DatabaseMode): Int {
        return try {
            observeAll(databaseMode).map { categories ->
                categories.maxOf { it.id }
            }.first()
        } catch (e: NoSuchElementException) {
            -1
        }
    }

    override suspend fun insert(category: Category) {
        if (observeDatabaseModeUseCase().first() == DatabaseMode.LOCAL) {
            localDataSource.insert(category.toEntityModel())
        } else {
            val id = getLastId(DatabaseMode.ONLINE) + 1
            remoteDataSource.insert(category.copy(id = id).toEntityModel())
        }
    }

    override suspend fun insertRemote(category: Category) {
        remoteDataSource.insert(category.toEntityModel())
    }

    override suspend fun update(category: Category) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.update(category.toEntityModel())
        } else {
            remoteDataSource.update(category.toEntityModel())
        }
    }

    override suspend fun delete(category: Category) {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.delete(category.toEntityModel())
        } else {
            remoteDataSource.delete(category.toEntityModel())
        }
    }

    override suspend fun deleteAllCurrentDatabase() {
        val databaseMode = observeDatabaseModeUseCase().first()
        if (databaseMode == DatabaseMode.LOCAL) {
            localDataSource.deleteAll()
        } else {
            remoteDataSource.deleteAll()
        }
    }

    override suspend fun deleteAllRemote() {
        remoteDataSource.deleteAll()
    }

    override suspend fun deleteAllLocal() {
        localDataSource.deleteAll()
    }
}
