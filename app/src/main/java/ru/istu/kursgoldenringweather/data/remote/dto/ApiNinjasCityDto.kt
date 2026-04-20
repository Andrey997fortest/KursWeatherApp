package ru.istu.kursgoldenringweather.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ApiNinjasCityDto(
    @SerializedName("name") val name: String?,
    @SerializedName("latitude") val latitude: Double?,
    @SerializedName("longitude") val longitude: Double?,
    @SerializedName("country") val country: String?,
    @SerializedName("population") val population: Int?,
)
