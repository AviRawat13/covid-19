package com.sarath.dev.covid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = "country")
class CountryEntity(
    @PrimaryKey
    @NotNull
    @ColumnInfo(name = "country") var country: String,
    @ColumnInfo(name = "slug") var slug: String?,
    @ColumnInfo(name = "code") var code: String?
)