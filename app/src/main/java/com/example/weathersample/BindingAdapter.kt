package com.example.weathersample

import android.util.Log
import android.widget.ImageView
import android.widget.RadioButton
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.weathersample.common.*
import com.example.weathersample.database.City
import com.example.weathersample.view.NextSevenDays.ForecastDayAdapter
import com.example.weathersample.view.home.CityAdapter
import com.example.weathersample.view.hourlyforecast.ForecastHourlyAdapter
import com.github.mikephil.charting.charts.LineChart


@BindingAdapter("floatText", "type")
fun bindTextView(textView: TextView, data: Float, type: Int){
    var text = ""
    when(type){
        TEMP -> text = data.toString() + "\u2103"
        HUMIDITY -> text = "$data%"
        WIND_SPEED -> {
            val windsp = String.format("%.2f km/hr", data.times(3.6))
            text = windsp
        }
    }
    textView.text = text
}

@BindingAdapter("backGround")
fun bindTextView(layout: ConstraintLayout, b: Boolean){
    val bg = if(b) R.drawable.night_bg else R.drawable.sunny_bg
    layout.setBackgroundResource(bg)
}

@BindingAdapter("city")
fun bindTextView(textView: TextView, weatherResponse: WeatherResponse){
    val city = weatherResponse.name
    textView.text = city
}



@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, list: List<ForeCastType>?) {
    if(list != null){
        if (list[0] is Daily){
            val adapter = recyclerView.adapter as ForecastDayAdapter
            val dailyData = ArrayList<Daily>()
            for (data in list){
                dailyData.add(data as Daily)
            }
            adapter.submitList(dailyData)
        }
        else if (list[0] is Hourly){
            val adapter = recyclerView.adapter as ForecastHourlyAdapter
            val hourlyData = ArrayList<Hourly>()
            for (i in list.indices step 3){
                hourlyData.add(list[i] as Hourly)
            }
            adapter.submitList(hourlyData)
        }
        else{
            val adapter = recyclerView.adapter as CityAdapter
            val cities = ArrayList<City>()
            for (i in list){
                cities.add(i as City)
                Log.d("AAA", i.cityName)
            }

            adapter.submitList(cities)
        }
    }

}

@BindingAdapter("day", "textType")
fun bindTextView(textView: TextView, dt: Long, type: Int){

    var date = ""
    when(type){
        1-> date =  getDayOfWeek(dt.times(1000)) + "\n" + getDayOfMonth(dt.times(1000))
        2-> date =  getDayOfWeek(dt.times(1000))
        3-> date = getHour(dt.times(1000))
    }
        getDayOfWeek(dt.times(1000)) + "\n" + getDayOfMonth(dt.times(1000))
    textView.text = date
}

@BindingAdapter("imageResource")
fun bindImage(imgView: ImageView, weatherId: Int) {
    Glide.with(imgView.context)
            .load(getImageResource(weatherId))
            .into(imgView)
}

@BindingAdapter("chartData")
fun bindChartData(chart: LineChart, hourly: List<Hourly>?) {
    if(hourly != null){
        val list = ArrayList<Hourly>()
        for (i in hourly.indices step 3) {
            list.add(hourly[i])
        }
        setChartValues(list, chart)
    }
}

@BindingAdapter("check")
fun bindRadioButton(radioButton: RadioButton, boolean: Boolean) {
    radioButton.isChecked = boolean
}





