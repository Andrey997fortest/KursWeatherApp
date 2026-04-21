package ru.istu.kursgoldenringweather.ui.main

import androidx.annotation.StringRes
import ru.istu.kursgoldenringweather.data.model.GoldenRingCity
import ru.istu.kursgoldenringweather.data.model.WeatherForecast
import ru.istu.kursgoldenringweather.data.source.GoldenRingCitySource

data class WeatherUiState(
    val cities: List<GoldenRingCity> = GoldenRingCitySource.cities,
    val selectedCityId: String = GoldenRingCitySource.cities.first().id,
    val isLoading: Boolean = false,
    val forecast: WeatherForecast? = null,
    @StringRes val errorMessageRes: Int? = null,
)
