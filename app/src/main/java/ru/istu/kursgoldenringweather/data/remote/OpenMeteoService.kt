package ru.istu.kursgoldenringweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.istu.kursgoldenringweather.data.remote.dto.OpenMeteoForecastDto

interface OpenMeteoService {

    @GET("v1/forecast")
    suspend fun getForecast(
        @Query("latitude") latitude: Double,
        @Query("longitude") longitude: Double,
        @Query("current") current: String =
            "temperature_2m,weather_code,wind_speed_10m",
        @Query("daily") daily: String =
            "weather_code,temperature_2m_max,temperature_2m_min,precipitation_probability_max",
        @Query("timezone") timezone: String = "auto",
        @Query("forecast_days") forecastDays: Int = 7,
    ): OpenMeteoForecastDto
}
