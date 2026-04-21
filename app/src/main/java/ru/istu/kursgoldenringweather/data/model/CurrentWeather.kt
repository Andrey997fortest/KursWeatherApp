package ru.istu.kursgoldenringweather.data.model

data class CurrentWeather(
    val timeIso: String,
    val temperatureCelsius: Double,
    val weatherCode: Int,
    val windSpeedKmh: Double,
)
