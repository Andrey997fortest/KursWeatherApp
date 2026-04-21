package ru.istu.kursgoldenringweather.data.model

import androidx.annotation.StringRes

data class GoldenRingCity(
    val id: String,
    @StringRes val nameResId: Int,
    val apiQueryName: String,
    val fallbackCoordinates: CityCoordinates,
)
