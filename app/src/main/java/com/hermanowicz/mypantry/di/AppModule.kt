package com.hermanowicz.mypantry.di

import android.content.Context
import androidx.room.Room
import com.hermanowicz.mypantry.data.local.db.LOCAL_DB_NAME
import com.hermanowicz.mypantry.data.local.db.LocalDb
import com.hermanowicz.mypantry.data.local.db.ProductDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
}
