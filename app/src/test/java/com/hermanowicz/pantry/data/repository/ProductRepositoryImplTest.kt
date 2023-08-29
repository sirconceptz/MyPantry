package com.hermanowicz.pantry.data.repository

import com.hermanowicz.pantry.data.local.model.ProductEntity
import com.hermanowicz.pantry.data.mapper.toDomainModel
import com.hermanowicz.pantry.di.local.dataSource.ProductLocalDataSource
import com.hermanowicz.pantry.di.remote.dataSource.ProductRemoteDataSource
import com.hermanowicz.pantry.domain.settings.ObserveDatabaseModeUseCase
import com.hermanowicz.pantry.utils.enums.DatabaseMode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ProductRepositoryImplTest {

    private lateinit var productRepository: ProductRepositoryImpl

    private val localDataSource: ProductLocalDataSource = mockk()
    private val remoteDataSource: ProductRemoteDataSource = mockk()
    private val observeDatabaseModeUseCase: ObserveDatabaseModeUseCase = mockk()

    private val productEntity: ProductEntity = ProductEntity(1, "Product 1")

    @Before
    fun setup() {
        productRepository = ProductRepositoryImpl(localDataSource, remoteDataSource, observeDatabaseModeUseCase)
    }

    @Test
    fun `observeById should return correct product from local data source`() = runTest {
        val product = productEntity.toDomainModel()
        val flow: Flow<ProductEntity> = flowOf(productEntity)
        coEvery { localDataSource.observeById(1) } returns flow

        productRepository.observeById(1, DatabaseMode.LOCAL).collect { observedProduct ->
            assertEquals(product, observedProduct)
            coVerify { localDataSource.observeById(1) }
        }
    }

    @Test
    fun `observeAll should return correct list of products from remote data source`() = runTest {
        val products = listOf(productEntity)
        val flow: Flow<List<ProductEntity>> = flowOf(products)
        coEvery { remoteDataSource.observeAll() } returns flow

        productRepository.observeAll(DatabaseMode.ONLINE).collect { productList ->
            assert(products.map { product -> product.toDomainModel() } == productList)
            coVerify { remoteDataSource.observeAll() }
        }
    }

    @Test
    fun `getLastId should return correct id`() = runBlocking {
        val products = listOf(productEntity)
        val flow: Flow<List<ProductEntity>> = flowOf(products)
        coEvery { localDataSource.observeAll() } returns flow

        val lastId = productRepository.getLastId(DatabaseMode.LOCAL)

        assertEquals(1, lastId)
        coVerify { localDataSource.observeAll() }
    }

    @Test
    fun `insert should insert products into local data source`() = runBlocking {
        val products = listOf(productEntity)
        coEvery { observeDatabaseModeUseCase() } returns flowOf(DatabaseMode.LOCAL)
        coEvery { localDataSource.insert(products) } returns listOf(1)

        val insertedIds = productRepository.insert(products.map { it.toDomainModel() })

        assertEquals(listOf(1L), insertedIds)
        coVerify { localDataSource.insert(products) }
    }

    @Test
    fun `update should update products in local data source`() = runBlocking {
        val products = listOf(productEntity)
        coEvery { observeDatabaseModeUseCase() } returns flowOf(DatabaseMode.LOCAL)
        coEvery { localDataSource.update(products) } returns Unit

        productRepository.update(products.map { it.toDomainModel() })

        coVerify { localDataSource.update(products) }
    }

    @Test
    fun `update should update products in remote data source`() = runBlocking {
        val products = listOf(productEntity)
        coEvery { observeDatabaseModeUseCase() } returns flowOf(DatabaseMode.ONLINE)
        coEvery { remoteDataSource.update(products) } returns Unit

        productRepository.update(products.map { it.toDomainModel() })

        coVerify { remoteDataSource.update(products) }
    }

    @Test
    fun `delete should delete products from local data source`() = runBlocking {
        val productIds = listOf(1)
        coEvery { observeDatabaseModeUseCase() } returns flowOf(DatabaseMode.LOCAL)
        coEvery { localDataSource.delete(productIds) } returns Unit

        productRepository.delete(productIds)

        coVerify { localDataSource.delete(productIds) }
    }

    @Test
    fun `delete should delete products from remote data source`() = runBlocking {
        val productIds = listOf(1)
        coEvery { observeDatabaseModeUseCase() } returns flowOf(DatabaseMode.ONLINE)
        coEvery { remoteDataSource.delete(productIds) } returns Unit

        productRepository.delete(productIds)

        coVerify { remoteDataSource.delete(productIds) }
    }

    @Test
    fun `deleteAllCurrentDatabase should delete all products from local data source`() = runBlocking {
        coEvery { observeDatabaseModeUseCase() } returns flowOf(DatabaseMode.LOCAL)
        coEvery { localDataSource.deleteAll() } returns Unit

        productRepository.deleteAllCurrentDatabase()

        coVerify { localDataSource.deleteAll() }
    }

    @Test
    fun `deleteAllCurrentDatabase should delete all products from remote data source`() = runBlocking {
        coEvery { observeDatabaseModeUseCase() } returns flowOf(DatabaseMode.ONLINE)
        coEvery { remoteDataSource.deleteAll() } returns Unit

        productRepository.deleteAllCurrentDatabase()

        coVerify { remoteDataSource.deleteAll() }
    }

    @Test
    fun `deleteAllRemote should delete all products from remote data source`() = runBlocking {
        coEvery { remoteDataSource.deleteAll() } returns Unit

        productRepository.deleteAllRemote()

        coVerify { remoteDataSource.deleteAll() }
    }

    @Test
    fun `deleteAllLocal should delete all products from local data source`() = runBlocking {
        coEvery { localDataSource.deleteAll() } returns Unit

        productRepository.deleteAllLocal()

        coVerify { localDataSource.deleteAll() }
    }
}
