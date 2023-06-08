package com.hermanowicz.pantry.di.repository

import com.hermanowicz.pantry.data.repository.ScannerRepositoryImpl
import com.journeyapps.barcodescanner.ScanOptions
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

interface ScannerRepository {
    suspend fun buildScanOptions(): ScanOptions
    fun decodeScanQRCodeResult(scanResult: String): Pair<Int, String>
}

@Module
@InstallIn(SingletonComponent::class)
abstract class ScannerRepositoryModule {

    @Binds
    abstract fun bindScannerRepository(
        scannerRepositoryImpl: ScannerRepositoryImpl
    ): ScannerRepository
}
