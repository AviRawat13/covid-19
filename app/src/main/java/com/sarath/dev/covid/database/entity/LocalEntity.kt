package com.sarath.dev.covid.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "local")
class LocalEntity(
    @PrimaryKey(autoGenerate = true) var id: Int?,
    @ColumnInfo(name = "country") var country: String?,
    @ColumnInfo(name = "country_slug") var countrySlug: String?,
    @ColumnInfo(name = "total_confirmed") var totalConfirmed: Int?,
    @ColumnInfo(name = "new_confirmed") var newConfirmed: Int?,
    @ColumnInfo(name = "total_recovered") var totalRecovered: Int?,
    @ColumnInfo(name = "new_recovered") var newRecovered: Int?,
    @ColumnInfo(name = "total_deceased") var totalDeceased: Int?,
    @ColumnInfo(name = "new_deceased") var newDeceased: Int?
) {

}

