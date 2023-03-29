package com.hermanowicz.mypantry.di

import android.content.Context
import androidx.room.Room
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.hermanowicz.mypantry.data.local.db.CategoriesDao
import com.hermanowicz.mypantry.data.local.db.LOCAL_DB_NAME
import com.hermanowicz.mypantry.data.local.db.LocalDb
import com.hermanowicz.mypantry.data.local.db.ProductDao
import com.hermanowicz.mypantry.data.local.db.StorageLocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext context: Context): LocalDb {
        return Room.databaseBuilder(
            context,
            LocalDb::class.java, LOCAL_DB_NAME
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    @Provides
    @Singleton
    fun provideProductDao(localDb: LocalDb): ProductDao {
        return localDb.productDao()
    }

    @Provides
    @Singleton
    fun provideStorageLocationDao(localDb: LocalDb): StorageLocationDao {
        return localDb.storageLocationDao()
    }

    @Provides
    @Singleton
    fun provideCategoriesDao(localDb: LocalDb): CategoriesDao {
        return localDb.categoriesDao()
    }

    @ViewModelScoped
    @Provides
    fun provideBarCodeOptions(): GmsBarcodeScannerOptions {
        return GmsBarcodeScannerOptions.Builder()
            .setBarcodeFormats(Barcode.FORMAT_ALL_FORMATS)
            .build()
    }

    @ViewModelScoped
    @Provides
    fun provideBarCodeScanner(
        context: Context,
        options: GmsBarcodeScannerOptions
    ): GmsBarcodeScanner {
        return GmsBarcodeScanning.getClient(context, options)
    }
}
