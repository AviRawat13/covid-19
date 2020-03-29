package com.sarath.dev.covid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "country")
class CountryEntity(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "country") var country: String?,
    @ColumnInfo(name = "slug") var slug: String?,
    @ColumnInfo(name = "code") var code: String?
)