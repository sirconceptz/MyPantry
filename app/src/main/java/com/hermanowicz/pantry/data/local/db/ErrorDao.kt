package com.hermanowicz.pantry.data.local.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.hermanowicz.pantry.data.local.model.ErrorEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ErrorDao {

    @Query("SELECT * FROM error")
    fun observeAll(): Flow<List<ErrorEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(vararg error: ErrorEntity)
}
