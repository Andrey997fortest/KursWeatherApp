package ru.istu.kursgoldenringweather.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.istu.kursgoldenringweather.data.model.DailyForecast
import ru.istu.kursgoldenringweather.databinding.ItemDailyForecastBinding

class DailyForecastAdapter : RecyclerView.Adapter<DailyForecastViewHolder>() {

    private var items: List<DailyForecast> = emptyList()

    @SuppressLint("NotifyDataSetChanged")
    fun update(items: List<DailyForecast>) {
        this.items = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DailyForecastViewHolder {
        val binding = ItemDailyForecastBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return DailyForecastViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DailyForecastViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size
}
