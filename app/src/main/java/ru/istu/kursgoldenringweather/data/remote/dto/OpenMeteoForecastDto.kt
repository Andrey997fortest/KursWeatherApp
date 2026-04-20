package ru.istu.kursgoldenringweather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class OpenMeteoForecastDto(
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("current") val current: OpenMeteoCurrentDto?,
    @SerializedName("daily") val daily: OpenMeteoDailyDto?,
)
