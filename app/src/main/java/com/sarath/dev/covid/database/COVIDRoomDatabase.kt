package com.sarath.dev.covid.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.sarath.dev.covid.COVID19
import com.sarath.dev.covid.database.dao.CountryDao
import com.sarath.dev.covid.database.dao.LocalDao
import com.sarath.dev.covid.database.entity.CountryEntity
import com.sarath.dev.covid.database.entity.LocalEntity

@Database(entities = [CountryEntity::class, LocalEntity::class], version = 1, exportSchema = false)
public abstract class COVIDRoomDatabase: RoomDatabase() {
    abstract fun localDao(): LocalDao
    abstract fun countryDao(): CountryDao

    companion object {
        @Volatile
        private var INSTANCE: COVIDRoomDatabase? = null

        fun getDatabase(context: Context): COVIDRoomDatabase {
            val tempInstance = INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    COVID19.context!!,
                    COVIDRoomDatabase::class.java,
                    "covid_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }
}