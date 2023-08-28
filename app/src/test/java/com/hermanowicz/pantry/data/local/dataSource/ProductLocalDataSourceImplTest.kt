package com.hermanowicz.pantry.data.local.dataSource

import com.hermanowicz.pantry.data.local.db.ProductDao
import com.hermanowicz.pantry.data.local.model.ProductEntity
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations

class ProductLocalDataSourceImplTest {

    @Mock
    private lateinit var productDao: ProductDao

    private lateinit var productLocalDataSource: ProductLocalDataSourceImpl

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        productLocalDataSource = ProductLocalDataSourceImpl(productDao)
    }

    @Test
    fun `test observeAll`() = runBlocking {
        val products = listOf(ProductEntity(1, "Product 1"))
        `when`(productDao.observeAll()).thenReturn(flowOf(products))

        val result = productLocalDataSource.observeAll().toList()

        assert(result[0] == products)
    }

    @Test
    fun `test getAll`() = runBlocking {
        val products = listOf(ProductEntity(1, "Product 1"))
        `when`(productDao.getAll()).thenReturn(products)

        val result = productLocalDataSource.getAll()

        assert(result == products)
    }

    @Test
    fun `test observeById`() = runBlocking {
        val product = ProductEntity(1, "Product 1")
        `when`(productDao.observeById(1)).thenReturn(flowOf(product))

        val result = productLocalDataSource.observeById(1).toList()

        assert(result[0] == product)
    }

    @Test
    fun `test insert`() = runBlocking {
        val products = listOf(ProductEntity(1, "Product 1"))
        val expectedIds = listOf(1L)
        `when`(productLocalDataSource.insert(products)).thenReturn(listOf(1L))

        val ids = productLocalDataSource.insert(products)
        assert(ids == expectedIds)
    }

    @Test
    fun `test update`() = runBlocking {
        val products = listOf(ProductEntity(1, "Product 1"))

        productLocalDataSource.update(products)

        verify(productDao).update(products[0])
    }

    @Test
    fun `test delete`() = runBlocking {
        val productIds = listOf(1, 2, 3)

        productLocalDataSource.delete(productIds)

        productIds.forEach { id ->
            verify(productDao).delete(id)
        }
    }

    @Test
    fun `test deleteAll`() = runBlocking {
        productLocalDataSource.deleteAll()

        verify(productDao).deleteAll()
    }
}
