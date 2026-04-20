package ru.istu.kursgoldenringweather.ui.main

import androidx.recyclerview.widget.RecyclerView
import kotlin.math.roundToInt
import ru.istu.kursgoldenringweather.R
import ru.istu.kursgoldenringweather.data.model.DailyForecast
import ru.istu.kursgoldenringweather.databinding.ItemDailyForecastBinding
import ru.istu.kursgoldenringweather.util.DateFormatter
import ru.istu.kursgoldenringweather.util.WeatherCodeMapper

class DailyForecastViewHolder(
    private val binding: ItemDailyForecastBinding,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: DailyForecast) = with(binding) {
        val context = root.context
        dateText.text = DateFormatter.formatDate(item.dateIso, context)
        weatherIconText.text = WeatherCodeMapper.icon(item.weatherCode)
        weatherDescriptionText.text = context.getString(WeatherCodeMapper.descriptionRes(item.weatherCode))
        temperatureRangeText.text = context.getString(
            R.string.temp_range_format,
            item.minTemperatureCelsius.roundToInt(),
            item.maxTemperatureCelsius.roundToInt(),
        )
        precipitationText.text = context.getString(
            R.string.precipitation_probability_format,
            item.precipitationProbabilityPercent.roundToInt(),
        )
    }
}
