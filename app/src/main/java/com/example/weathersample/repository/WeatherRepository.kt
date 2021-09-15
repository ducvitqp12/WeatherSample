package com.example.weathersample.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.example.weathersample.database.City
import com.example.weathersample.database.CityDatabase

class WeatherRepository(private val database: CityDatabase) {
    val cities : List<City> = database.cityDAO().getItems()

    suspend fun updateDB(city: City): Long{
        return database.cityDAO().insert(city)
    }

    var a=0

    fun change(){
        a=1
        Log.d("check", "asd")
    }
}