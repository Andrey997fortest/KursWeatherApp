package ru.istu.kursgoldenringweather.data.source

import ru.istu.kursgoldenringweather.R
import ru.istu.kursgoldenringweather.data.model.CityCoordinates
import ru.istu.kursgoldenringweather.data.model.GoldenRingCity

object GoldenRingCitySource {

    val cities: List<GoldenRingCity> = listOf(
        GoldenRingCity(
            id = "sergiev_posad",
            nameResId = R.string.city_sergiev_posad,
            apiQueryName = "Sergiyev Posad",
            fallbackCoordinates = CityCoordinates(56.3150, 38.1353),
        ),
        GoldenRingCity(
            id = "pereslavl_zalessky",
            nameResId = R.string.city_pereslavl_zalessky,
            apiQueryName = "Pereslavl-Zalessky",
            fallbackCoordinates = CityCoordinates(56.7360, 38.8540),
        ),
        GoldenRingCity(
            id = "rostov_veliky",
            nameResId = R.string.city_rostov_veliky,
            apiQueryName = "Rostov",
            fallbackCoordinates = CityCoordinates(57.1848, 39.4149),
        ),
        GoldenRingCity(
            id = "yaroslavl",
            nameResId = R.string.city_yaroslavl,
            apiQueryName = "Yaroslavl",
            fallbackCoordinates = CityCoordinates(57.6261, 39.8845),
        ),
        GoldenRingCity(
            id = "kostroma",
            nameResId = R.string.city_kostroma,
            apiQueryName = "Kostroma",
            fallbackCoordinates = CityCoordinates(57.7679, 40.9269),
        ),
        GoldenRingCity(
            id = "ivanovo",
            nameResId = R.string.city_ivanovo,
            apiQueryName = "Ivanovo",
            fallbackCoordinates = CityCoordinates(56.9995, 40.9728),
        ),
        GoldenRingCity(
            id = "suzdal",
            nameResId = R.string.city_suzdal,
            apiQueryName = "Suzdal",
            fallbackCoordinates = CityCoordinates(56.4197, 40.4495),
        ),
        GoldenRingCity(
            id = "vladimir",
            nameResId = R.string.city_vladimir,
            apiQueryName = "Vladimir",
            fallbackCoordinates = CityCoordinates(56.1290, 40.4066),
        ),
    )

    fun findById(cityId: String): GoldenRingCity? = cities.firstOrNull { it.id == cityId }
}
