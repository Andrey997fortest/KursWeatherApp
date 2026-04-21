package ru.istu.kursgoldenringweather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenMeteoCurrentDto(
    @SerializedName("time") val time: String?,
    @SerializedName("temperature_2m") val temperature2m: Double?,
    @SerializedName("weather_code") val weatherCode: Int?,
    @SerializedName("wind_speed_10m") val windSpeed10m: Double?,
)
