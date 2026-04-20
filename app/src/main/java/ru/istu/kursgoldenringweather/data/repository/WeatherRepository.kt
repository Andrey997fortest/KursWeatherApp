package ru.istu.kursgoldenringweather.data.repository

import ru.istu.kursgoldenringweather.data.model.WeatherForecast

interface WeatherRepository {
    suspend fun getForecast(cityId: String): WeatherForecast
}
