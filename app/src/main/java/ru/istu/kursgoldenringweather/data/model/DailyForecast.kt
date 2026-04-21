package ru.istu.kursgoldenringweather.data.model

data class DailyForecast(
    val dateIso: String,
    val weatherCode: Int,
    val minTemperatureCelsius: Double,
    val maxTemperatureCelsius: Double,
    val precipitationProbabilityPercent: Double,
)
