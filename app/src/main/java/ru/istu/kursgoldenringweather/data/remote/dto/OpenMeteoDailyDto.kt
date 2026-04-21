package ru.istu.kursgoldenringweather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenMeteoDailyDto(
    @SerializedName("time") val time: List<String>?,
    @SerializedName("weather_code") val weatherCode: List<Int>?,
    @SerializedName("temperature_2m_max") val temperatureMax: List<Double>?,
    @SerializedName("temperature_2m_min") val temperatureMin: List<Double>?,
    @SerializedName("precipitation_probability_max") val precipitationProbabilityMax: List<Double>?,
)
