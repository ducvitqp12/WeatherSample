package com.example.weathersample

import com.example.weathersample.common.baseUrl
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherService {
    @GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("q") q: String,
        @Query("appid") appId: String,
        @Query("units") unit: String
    ): WeatherResponse

    @GET("data/2.5/onecall")
    suspend fun getOneCallData(
        @Query("lat") lat: String,
        @Query("lon") lon: String,
        @Query("appid") apiKey: String,
        @Query("exclude") exclude: String,
        @Query("units") unit: String
    ): OneCallResponse
}


private val gson = GsonBuilder()
    .setDateFormat("yyyy-MM-dd HH:mm:ss")
    .create()

private val retrofit = Retrofit.Builder()
    .baseUrl(baseUrl)
    .addConverterFactory(GsonConverterFactory.create(gson))
    .build()

object WeatherAPI {
    val retrofitService: WeatherService by lazy { retrofit.create(WeatherService::class.java) }
}