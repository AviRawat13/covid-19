package com.sarath.dev.covid.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.sarath.dev.covid.database.entity.CountryEntity

@Dao
interface CountryDao {
    @Query("SELECT * FROM COUNTRY ORDER BY country ASC")
    fun getCountries(): LiveData<List<CountryEntity>>

    @Query("SELECT * FROM COUNTRY WHERE country = :countryName")
    fun getEntity(countryName: String?): LiveData<List<CountryEntity>>

    @Query("SELECT code FROM COUNTRY WHERE country = :countryName")
    fun getCode(countryName: String?): LiveData<List<String>>

    @Query("SELECT * FROM COUNTRY WHERE country = :countryName")
    fun getSlug(countryName: String?): LiveData<List<CountryEntity>>

    @Query("UPDATE COUNTRY SET slug = :slug WHERE country = :country")
    fun update(country: String, slug: String)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(countryEntity: CountryEntity)
}