package com.hermanowicz.pantry.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import com.hermanowicz.pantry.data.local.db.CategoryDao
import com.hermanowicz.pantry.data.local.db.LOCAL_DB_NAME
import com.hermanowicz.pantry.data.local.db.LocalDb
import com.hermanowicz.pantry.data.local.db.ProductDao
import com.hermanowicz.pantry.data.local.db.StorageLocationDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

private const val DATA_STORE_FILE = "user_prefs"

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
    fun provideCategoriesDao(localDb: LocalDb): CategoryDao {
        return localDb.categoriesDao()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATA_STORE_FILE) }
        )
    }
}
