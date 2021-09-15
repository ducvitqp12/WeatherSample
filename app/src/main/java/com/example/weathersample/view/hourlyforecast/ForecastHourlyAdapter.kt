package com.example.weathersample.view.hourlyforecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.weathersample.Hourly
import com.example.weathersample.databinding.HourlyItemBinding

class ForecastHourlyAdapter: ListAdapter<Hourly,
        ForecastHourlyAdapter.HourlyForecastViewHolder>(DiffCallback) {

    class HourlyForecastViewHolder(private var binding: HourlyItemBinding):
        RecyclerView.ViewHolder(binding.root) {
        fun bind(hourly: Hourly) {
            binding.hourly = hourly
            // This is important, because it forces the data binding to execute immediately,
            // which allows the RecyclerView to make the correct view size measurements
            binding.executePendingBindings()
        }
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Hourly>() {
        override fun areItemsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: Hourly, newItem: Hourly): Boolean {
            return oldItem.dt == newItem.dt
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourlyForecastViewHolder {
        return HourlyForecastViewHolder(HourlyItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: HourlyForecastViewHolder, position: Int) {
        val hourlyForecast = getItem(position)
//        holder.itemView.setOnClickListener {
//            onClickListener.onClick(marsProperty)
////        }
        holder.bind(hourlyForecast)
    }
}

