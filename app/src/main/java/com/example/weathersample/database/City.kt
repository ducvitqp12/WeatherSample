package com.example.weathersample.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.weathersample.ForeCastType
import java.text.NumberFormat
import java.util.*

@Entity
data class City(
    @PrimaryKey
    @ColumnInfo(name = "id")
    val id: Int = 0,
    @ColumnInfo(name = "name")
    val cityName: String,
    @ColumnInfo(name = "country")
    val country: String
): ForeCastType

//fun List<City>.asDomainModel(): List<City> {
//    return map {
//        City(
//            id = it.id,
//            cityName = it.cityName,
//            country = it.country)
//    }
//}
