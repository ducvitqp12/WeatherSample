package com.example.weathersample.view.home

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathersample.Daily
import com.example.weathersample.database.City
import com.example.weathersample.databinding.CityItemBinding
import com.example.weathersample.databinding.ForecastDayItemBinding
import com.example.weathersample.view.NextSevenDays.ForecastDayAdapter

class CityAdapter:  ListAdapter<City,
        CityAdapter.CityViewHolder>(DiffCallback) {

    class CityViewHolder(private var binding: CityItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(city: City) {
            binding.city = city
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityViewHolder {
        Log.d("AAA", "Created")
        return CityViewHolder(
            CityItemBinding.inflate(
                LayoutInflater.from(parent.context)
            )
        )
    }

    override fun onBindViewHolder(holder: CityViewHolder, position: Int) {
        val city = getItem(position)
        holder.bind(city)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<City>() {
        override fun areItemsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: City, newItem: City): Boolean {
            return oldItem.id == newItem.id
        }
    }
}