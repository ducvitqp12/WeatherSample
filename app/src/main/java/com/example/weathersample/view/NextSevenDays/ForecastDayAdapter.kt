package com.example.weathersample.view.NextSevenDays

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathersample.Daily
import com.example.weathersample.databinding.ForecastDayItemBinding


class ForecastDayAdapter:  ListAdapter<Daily,
        ForecastDayAdapter.DailyForecastViewHolder>(DiffCallback) {

    class DailyForecastViewHolder(private var binding: ForecastDayItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(daily: Daily) {
            binding.daily = daily
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Daily>() {
        override fun areItemsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Daily, newItem: Daily): Boolean {
            return oldItem.dt == newItem.dt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        return DailyForecastViewHolder(ForecastDayItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        val dailyForecast = getItem(position)
        holder.bind(dailyForecast)
    }
}

