package com.hermanowicz.mypantry.di.repository

import com.hermanowicz.mypantry.data.repository.ScannerRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.flow.Flow

interface ScannerRepository {
    fun startScanning(): Flow<String?>
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ScannerRepositoryModule {

    @Binds
    abstract fun bindScannerRepository(
        scannerRepositoryImpl: ScannerRepositoryImpl
    ): ScannerRepository
}
