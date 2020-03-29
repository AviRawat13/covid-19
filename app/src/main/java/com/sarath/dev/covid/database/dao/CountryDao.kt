package com.sarath.dev.covid.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.sarath.dev.covid.database.entity.CountryEntity

@Dao
abstract interface CountryDao {
    @Query("SELECT * FROM COUNTRY ORDER BY country ASC")
    fun getCountries(): LiveData<List<CountryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryEntity: CountryEntity)
}