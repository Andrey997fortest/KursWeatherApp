package ru.istu.kursgoldenringweather.data.model

data class WeatherForecast(
    val city: GoldenRingCity,
    val coordinates: CityCoordinates,
    val coordinateSource: CoordinateSource,
    val currentWeather: CurrentWeather,
    val dailyForecast: List<DailyForecast>,
)
