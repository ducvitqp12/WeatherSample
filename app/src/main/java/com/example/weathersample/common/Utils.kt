package com.example.weathersample.common

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Color
import androidx.core.os.ConfigurationCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.example.weathersample.Hourly
import com.example.weathersample.R
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import java.util.*

private const val NAME = "Weather"
private const val MODE = Context.MODE_PRIVATE

const val baseUrl = "https://api.openweathermap.org/"
const val AppId = "9be42206602f9889922d5936af28d9a1"
const val unit = "metric"
const val dailyExclude = "current,minutely,hourly,alerts"
const val hourlyExclude = "current,minutely,daily,alerts"
const val TEMP = 0
const val HUMIDITY = 1
const val WIND_SPEED = 2
const val BACKGROUND_KEY = "background"
const val HOME_COLOR_KEY = "home"
var extended = false
lateinit var preferences: SharedPreferences

fun init(context: Context){
    preferences = context.getSharedPreferences(NAME, MODE)
}

fun showFragment(viewId:Int, fragment: Fragment, fragmentManager: FragmentManager, withAnimation: Boolean) {
    val transaction = fragmentManager.beginTransaction()
    if (withAnimation) {
        transaction.setCustomAnimations(R.anim.slide_up_anim, R.anim.slide_down_anim)
    } else {
        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
    }
    transaction.add(viewId , fragment).addToBackStack(null).commit()
}
fun getDayOfWeek(ms : Long): String{
    val date = Calendar.getInstance()
    date.timeInMillis = ms
    return when(date.get(Calendar.DAY_OF_WEEK)){
        Calendar.MONDAY -> "Monday"
        Calendar.TUESDAY -> "Tuesday"
        Calendar.WEDNESDAY -> "Wednesday"
        Calendar.FRIDAY -> "Friday"
        Calendar.THURSDAY -> "Thursday"
        Calendar.SATURDAY -> "Saturday"
        else -> "Sunday"
    }
}

fun getHour(ms : Long): String{
    val date = Calendar.getInstance()
    date.timeInMillis = ms
    return "${date.get(Calendar.HOUR_OF_DAY)}:00"
}

fun getDayOfMonth(ms : Long): String{
    val date = Calendar.getInstance()
    date.timeInMillis = ms
    return getMonth(date.get(Calendar.MONTH)) + " " + date.get(Calendar.DAY_OF_MONTH)
}

fun getMonth(int: Int): String{
    return when(int){
        Calendar.JANUARY -> "January"
        Calendar.FEBRUARY -> "February"
        Calendar.MARCH -> "March"
        Calendar.APRIL -> "April"
        Calendar.MAY -> "May"
        Calendar.JUNE -> "June"
        Calendar.JULY -> "July"
        Calendar.AUGUST -> "August"
        Calendar.SEPTEMBER -> "September"
        Calendar.OCTOBER -> "October"
        Calendar.NOVEMBER -> "November"
        else -> "December"
    }
}

fun getImageResource(weatherCode: Int): Int{
    when {
        weatherCode / 100 == 2 -> {
            return R.drawable.ic_storm_weather
        }
        weatherCode / 100 == 3 -> {
            return R.drawable.ic_rainy_weather
        }
        weatherCode / 100 == 5 -> {
            return R.drawable.ic_rainy_weather
        }
        weatherCode / 100 == 6 -> {
            return R.drawable.ic_snow_weather
        }
        weatherCode / 100 == 7 -> {
            return R.drawable.ic_unknown
        }
        weatherCode == 800 -> {
            return R.drawable.ic_clear_day
        }
        weatherCode == 801 -> {
            return R.drawable.ic_few_clouds
        }
        weatherCode == 803 -> {
            return R.drawable.ic_broken_clouds
        }
        weatherCode / 100 == 8 -> {
            return R.drawable.ic_cloudy_weather
        }
        else -> return R.drawable.ic_unknown
    }
}

fun setChartValues(itemHourlyList: List<Hourly>, chart: LineChart) {
    val entries: MutableList<Entry> = ArrayList()
    var i = 0
    if (isRTL(chart.context)) {
        var j = itemHourlyList.size - 1
        while (j >= 0) {
            entries.add(Entry(i.toFloat(), itemHourlyList[j].temp ))
            i++
            j--
        }
    } else {
        for (itemHourlyDB in itemHourlyList) {
            entries.add(Entry(i.toFloat(), itemHourlyDB.temp))
            i++
        }
    }
    val dataSet = LineDataSet(entries, "Label") // add entries to dataset
    dataSet.lineWidth = 4f
    dataSet.circleRadius = 7f
    dataSet.isHighlightEnabled = false
    dataSet.setCircleColor(Color.parseColor("#33b5e5"))
    dataSet.color = R.color.purple_500
    dataSet.valueTextSize = 12f
    dataSet.valueTextColor = Color.WHITE
    dataSet.mode = LineDataSet.Mode.CUBIC_BEZIER
    dataSet.valueFormatter = object : ValueFormatter() {
        override fun getFormattedValue(value: Float): String {
            return String.format(Locale.getDefault(), "%.1f", value)
        }
    }
    val lineData = LineData(dataSet)
    chart.description.isEnabled = true
    chart.description.text = "Next 48 hours"
    chart.description.xOffset = 2f
    chart.description.yOffset = -10f
    chart.description.textSize = 8f
    chart.axisLeft.setDrawLabels(false)
    chart.axisRight.setDrawLabels(false)
    chart.xAxis.setDrawLabels(false)
    chart.legend.isEnabled = false // Hide the legend
    chart.xAxis.setDrawGridLines(false)
    chart.axisLeft.setDrawGridLines(false)
    chart.axisRight.setDrawGridLines(false)
    chart.axisLeft.setDrawAxisLine(false)
    chart.axisRight.setDrawAxisLine(false)
    chart.xAxis.setDrawAxisLine(false)
    chart.setScaleEnabled(false)
    chart.data = lineData
    chart.animateY(1000)
}

fun isRTL(context: Context): Boolean {
    val locale = ConfigurationCompat.getLocales(context.resources.configuration)[0]
    val directionality = Character.getDirectionality(locale.displayName[0]).toInt()
    return directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT.toInt() || directionality == Character.DIRECTIONALITY_RIGHT_TO_LEFT_ARABIC.toInt()
}

fun saveState(boolean: Boolean, color: Int){
    val preferencesEditor: SharedPreferences.Editor = preferences.edit()
    preferencesEditor.putBoolean(BACKGROUND_KEY, boolean)
    preferencesEditor.putInt(HOME_COLOR_KEY, color)
    preferencesEditor.apply()
}