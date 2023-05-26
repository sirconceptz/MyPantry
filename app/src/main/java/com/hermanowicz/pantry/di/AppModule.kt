package com.hermanowicz.pantry.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.PreferenceDataStoreFactory
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStoreFile
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.hermanowicz.pantry.data.local.db.CategoryDao
import com.hermanowicz.pantry.data.local.db.ErrorDao
import com.hermanowicz.pantry.data.local.db.LOCAL_DB_NAME
import com.hermanowicz.pantry.data.local.db.LocalDb
import com.hermanowicz.pantry.data.local.db.ProductDao
import com.hermanowicz.pantry.data.local.db.StorageLocationDao
import com.hermanowicz.pantry.data.remote.ErrorApiInterface
import com.hermanowicz.pantry.utils.Constants.DATA_STORE_FILE
import com.hermanowicz.pantry.utils.Constants.URL_ERROR_ALERT_SYSTEM_API
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @Provides
    @Singleton
    fun provideLocalDb(@ApplicationContext context: Context): LocalDb {
        return Room.databaseBuilder(
            context,
            LocalDb::class.java,
            LOCAL_DB_NAME
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

    @Provides
    @Singleton
    fun provideErrorsDao(localDb: LocalDb): ErrorDao {
        return localDb.errorDao()
    }

    @Singleton
    @Provides
    fun providePreferencesDataStore(@ApplicationContext context: Context): DataStore<Preferences> {
        return PreferenceDataStoreFactory.create(
            produceFile = { context.preferencesDataStoreFile(DATA_STORE_FILE) }
        )
    }

    @Singleton
    @Provides
    fun providesErrorAlertSystemApi(): ErrorApiInterface {
        val gson: Gson = GsonBuilder().setLenient().create()

        val httpLoggingInterceptor = HttpLoggingInterceptor()
        httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .readTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(60, TimeUnit.SECONDS)
            .build()

        return Retrofit.Builder()
            .baseUrl(URL_ERROR_ALERT_SYSTEM_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
            .create(ErrorApiInterface::class.java)
    }
}
