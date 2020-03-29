package com.sarath.dev.covid.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarath.dev.covid.database.entity.LocalEntity

@Dao
abstract interface LocalDao {
    @Query("SELECT * FROM local")
    fun getLocalData(): LiveData<List<LocalEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(localEntity: LocalEntity)
}