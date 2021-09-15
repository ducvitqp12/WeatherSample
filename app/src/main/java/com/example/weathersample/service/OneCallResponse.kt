package com.example.weathersample

import com.google.gson.annotations.SerializedName
import java.util.ArrayList

class OneCallResponse {
    @SerializedName("lat")
    var lat = 0f
    @SerializedName("lon")
    var lon = 0f
    @SerializedName("timezone")
    var timeZone : String? = null
    @SerializedName("timezone_offset")
    var timeOffset = 0
    @SerializedName("current")
    var current: Current? = null
    @SerializedName("hourly")
    var hourly: List<Hourly>? =  null
    @SerializedName("daily")
    var daily: List<Daily>? = null
}

class Daily : ForeCastType{
    @SerializedName("dt")
    var dt: Long = 0
    @SerializedName("sunrise")
    var sunRise: Long = 0
    @SerializedName("sunset")
    var sunSet: Long = 0
    @SerializedName("moonrise")
    var moonRise: Long = 0
    @SerializedName("moonset")
    var moonSet: Long = 0
    @SerializedName("moon_phase")
    val moonPhase = 0f
    @SerializedName("pressure")
    var pressure = 0
    @SerializedName("humidity")
    var humidity = 0
    @SerializedName("dew_point")
    var dewPoint = 0f
    @SerializedName("wind_speed")
    var windSpeed = 0f
    @SerializedName("wind_deg")
    var windDeg = 0f
    @SerializedName("uvi")
    var uvi = 0f
    @SerializedName("clouds")
    var clouds = 0
    @SerializedName("wind_gust")
    var windGust = 0f
    @SerializedName("rain")
    var rain = 0f
    @SerializedName("snow")
    var snow = 0f
    @SerializedName("weather")
    var weather: ArrayList<Weather> = ArrayList<Weather>()
    @SerializedName("pop")
    var pop = 0f
    @SerializedName("temp")
    var temp: Temp? = null
    @SerializedName("feels_like")
    var feelsLike: FeelsLike? = null

}

class FeelsLike {
    @SerializedName("day")
    var day =  0f
    @SerializedName("night")
    var night = 0f
    @SerializedName("eve")
    var eve = 0f
    @SerializedName("morn")
    var morn = 0f
}

class Temp {
    @SerializedName("day")
    var day =  0f
    @SerializedName("night")
    var night = 0f
    @SerializedName("eve")
    var eve = 0f
    @SerializedName("morn")
    var morn = 0f
    @SerializedName("min")
    var min = 0f
    @SerializedName("max")
    var max = 0f
}

class Hourly : ForeCastType {
    @SerializedName("dt")
    var dt: Long = 0
    @SerializedName("temp")
    var temp = 0f
    @SerializedName("feels_like")
    var feelsLike = 0f
    @SerializedName("pressure")
    var pressure = 0
    @SerializedName("humidity")
    var humidity = 0
    @SerializedName("dew_point")
    var dewPoint = 0f
    @SerializedName("uvi")
    var uvi = 0f
    @SerializedName("clouds")
    var clouds = 0f
    @SerializedName("visibility")
    var visibility = 0f
    @SerializedName("wind_speed")
    var windSpeed = 0f
    @SerializedName("wind_deg")
    var windDeg = 0f
    @SerializedName("wind_gust")
    var windGust = 0f
    @SerializedName("rain.1h")
    var rain = 0f
    @SerializedName("snow.1h")
    var snow = 0f
    @SerializedName("weather")
    var weather: ArrayList<Weather> = ArrayList<Weather>()
    @SerializedName("pop")
    var pop = 0f

}

class Current {
    @SerializedName("dt")
    var dt: Long = 0
    @SerializedName("sunrise")
    var sunRise: Long = 0
    @SerializedName("sunset")
    var sunSet: Long = 0
    @SerializedName("temp")
    var temp = 0f
    @SerializedName("feels_like")
    var feelsLike = 0f
    @SerializedName("pressure")
    var pressure = 0
    @SerializedName("humidity")
    var humidity = 0
    @SerializedName("dew_point")
    var dewPoint = 0f
    @SerializedName("uvi")
    var uvi = 0f
    @SerializedName("clouds")
    var clouds = 0
    @SerializedName("visibility")
    var visibility = 0
    @SerializedName("wind_speed")
    var windSpeed = 0f
    @SerializedName("wind_deg")
    var windDeg = 0f
    @SerializedName("rain.1h")
    var rain = 0f
    @SerializedName("snow.1h")
    var snow = 0f
    @SerializedName("weather")
    var weather: ArrayList<Weather> = ArrayList<Weather>()
}

interface ForeCastType