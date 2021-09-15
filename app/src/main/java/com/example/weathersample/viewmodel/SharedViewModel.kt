package com.example.weathersample

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.example.weathersample.common.AppId
import com.example.weathersample.common.dailyExclude
import com.example.weathersample.common.hourlyExclude
import com.example.weathersample.common.unit
import com.example.weathersample.database.City
import com.example.weathersample.database.CityDatabase
import com.example.weathersample.repository.WeatherRepository
import kotlinx.coroutines.launch
import kotlin.Exception

enum class WeatherApiStatus { LOADING, ERROR, DONE }

class SharedViewModel(application: Application): AndroidViewModel(application) {

    private val weatherRepository = WeatherRepository(CityDatabase.getDatabase(application))

    val cities = weatherRepository.cities

    private val _weather = MutableLiveData<WeatherResponse>()

    private val _weatherStatus = MutableLiveData<WeatherApiStatus>()

    val weatherStatus: LiveData<WeatherApiStatus>
        get() = _weatherStatus

    private val _hourlyStatus = MutableLiveData<WeatherApiStatus>()

    val hourlyStatus: LiveData<WeatherApiStatus>
        get() = _hourlyStatus

    private val _dailyStatus = MutableLiveData<WeatherApiStatus>()

    val dailyStatus: LiveData<WeatherApiStatus>
        get() = _dailyStatus

    val weather: LiveData<WeatherResponse>
        get() = _weather

    private val _hourly = MutableLiveData<List<Hourly>>()

    val hourly: LiveData<List<Hourly>>
        get() = _hourly

    private val _daily = MutableLiveData<List<Daily>>()

    val daily: LiveData<List<Daily>>
        get() = _daily

    private val _nightMode = MutableLiveData<Boolean>()

    val nightMode: LiveData<Boolean>
        get() = _nightMode

    private val _homeCardColor = MutableLiveData<Int>()

    val homeCardColor: LiveData<Int>
        get() = _homeCardColor

    fun setHomeColor(color:Int){
        _homeCardColor.value = color
    }

    fun setValue(boolean: Boolean){
        _nightMode.value = boolean
//        _nightMode.call()
    }

    fun getWeatherResponse(city: String){
        viewModelScope.launch {
            try {
                _weather.value = WeatherAPI.retrofitService.getCurrentWeather(city, AppId, unit)
                _weatherStatus.value = WeatherApiStatus.DONE
                Log.d("ABC", _weather.value?.name.toString())
            }
            catch (e: Exception){
                e.printStackTrace()
                _weatherStatus.value = WeatherApiStatus.ERROR
            }
        }
    }

    fun getHourlyResponse(lat: String, lon: String){
        viewModelScope.launch {
            try{
                _hourly.value = WeatherAPI.retrofitService.getOneCallData(lat, lon, AppId, hourlyExclude, unit).hourly
                _hourlyStatus.value = WeatherApiStatus.DONE
                hourly.value?.forEach { i ->
                    Log.d("ABC", i.dt.toString())
                }
            }
            catch (e: Exception){
                _hourlyStatus.value = WeatherApiStatus.ERROR
            }
        }
    }

    fun getA(){
        viewModelScope.launch {
            val insert =weatherRepository.updateDB(City(123, "ABC", "BCD"))
            Log.d("check1", insert.toString())
            Log.d("check", weatherRepository.a.toString())
            weatherRepository.change()
            Log.d("check", weatherRepository.a.toString())
            Log.d("check1", weatherRepository.cities.size.toString())
        }
    }

    fun updateRoom(city: City){
        viewModelScope.launch {
            try{
                weatherRepository.updateDB(city)
            }
            catch (e: Exception){
            }
        }
    }

    fun getDailyResponse(lat: String, lon: String){
        viewModelScope.launch {
            try{
                val oneCallResponse = WeatherAPI.retrofitService.getOneCallData(lat, lon, AppId, dailyExclude, unit)
                _daily.value = oneCallResponse.daily
                _dailyStatus.value = WeatherApiStatus.DONE
                Log.d("ABC", daily.value?.get(1)!!.dt.toString())
            }
            catch (e: Exception){
                _dailyStatus.value = WeatherApiStatus.ERROR
                Log.d("ABC", e.message.toString())
            }
        }
    }

    class Factory(private val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(SharedViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return SharedViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}