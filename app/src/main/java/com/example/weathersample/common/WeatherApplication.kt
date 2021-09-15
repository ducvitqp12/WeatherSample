package com.example.weathersample.common

import android.app.Application
import com.example.weathersample.database.CityDatabase

class WeatherApplication: Application() {
    val database:CityDatabase by lazy { CityDatabase.getDatabase(this) }
}