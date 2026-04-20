package ru.istu.kursgoldenringweather.data.repository

import java.io.IOException
import kotlin.math.pow
import ru.istu.kursgoldenringweather.data.model.CityCoordinates
import ru.istu.kursgoldenringweather.data.model.CoordinateSource
import ru.istu.kursgoldenringweather.data.model.CurrentWeather
import ru.istu.kursgoldenringweather.data.model.DailyForecast
import ru.istu.kursgoldenringweather.data.model.GoldenRingCity
import ru.istu.kursgoldenringweather.data.model.WeatherForecast
import ru.istu.kursgoldenringweather.data.remote.ApiNinjasService
import ru.istu.kursgoldenringweather.data.remote.OpenMeteoService
import ru.istu.kursgoldenringweather.data.remote.dto.ApiNinjasCityDto
import ru.istu.kursgoldenringweather.data.remote.dto.OpenMeteoForecastDto
import ru.istu.kursgoldenringweather.data.source.GoldenRingCitySource

class DefaultWeatherRepository(
    private val openMeteoService: OpenMeteoService,
    private val apiNinjasService: ApiNinjasService,
    private val citySource: GoldenRingCitySource,
    private val apiKey: String,
) : WeatherRepository {

    override suspend fun getForecast(cityId: String): WeatherForecast {
        val city = citySource.findById(cityId)
            ?: throw IllegalArgumentException("Unknown city id: $cityId")

        val resolvedCoordinates = resolveCoordinates(city)
        val response = openMeteoService.getForecast(
            latitude = resolvedCoordinates.first.latitude,
            longitude = resolvedCoordinates.first.longitude,
        )

        return mapForecast(
            city = city,
            coordinates = resolvedCoordinates.first,
            coordinateSource = resolvedCoordinates.second,
            response = response,
        )
    }

    private suspend fun resolveCoordinates(city: GoldenRingCity): Pair<CityCoordinates, CoordinateSource> {
        if (apiKey.isBlank()) {
            return city.fallbackCoordinates to CoordinateSource.FALLBACK
        }

        return try {
            val candidates = apiNinjasService.findCity(name = city.apiQueryName)
            val bestMatch = pickBestCandidate(candidates, city.fallbackCoordinates)
            if (bestMatch != null) {
                CityCoordinates(
                    latitude = bestMatch.latitude ?: city.fallbackCoordinates.latitude,
                    longitude = bestMatch.longitude ?: city.fallbackCoordinates.longitude,
                ) to CoordinateSource.API_NINJAS
            } else {
                city.fallbackCoordinates to CoordinateSource.FALLBACK
            }
        } catch (_: Exception) {
            city.fallbackCoordinates to CoordinateSource.FALLBACK
        }
    }

    private fun pickBestCandidate(
        candidates: List<ApiNinjasCityDto>,
        fallbackCoordinates: CityCoordinates,
    ): ApiNinjasCityDto? {
        return candidates
            .filter { it.latitude != null && it.longitude != null }
            .minByOrNull { dto ->
                (dto.latitude!! - fallbackCoordinates.latitude).pow(2) +
                    (dto.longitude!! - fallbackCoordinates.longitude).pow(2)
            }
    }

    private fun mapForecast(
        city: GoldenRingCity,
        coordinates: CityCoordinates,
        coordinateSource: CoordinateSource,
        response: OpenMeteoForecastDto,
    ): WeatherForecast {
        val currentDto = response.current ?: throw IOException("Empty current weather payload")
        val dailyDto = response.daily ?: throw IOException("Empty daily forecast payload")

        val current = CurrentWeather(
            timeIso = currentDto.time.orEmpty(),
            temperatureCelsius = currentDto.temperature2m ?: 0.0,
            weatherCode = currentDto.weatherCode ?: -1,
            windSpeedKmh = currentDto.windSpeed10m ?: 0.0,
        )

        val time = dailyDto.time.orEmpty()
        val codes = dailyDto.weatherCode.orEmpty()
        val mins = dailyDto.temperatureMin.orEmpty()
        val maxs = dailyDto.temperatureMax.orEmpty()
        val precipitation = dailyDto.precipitationProbabilityMax.orEmpty()

        val size = listOf(time.size, codes.size, mins.size, maxs.size, precipitation.size).minOrNull() ?: 0

        if (size == 0) {
            throw IOException("Empty daily forecast arrays")
        }

        val days = buildList {
            for (index in 0 until size) {
                add(
                    DailyForecast(
                        dateIso = time[index],
                        weatherCode = codes[index],
                        minTemperatureCelsius = mins[index],
                        maxTemperatureCelsius = maxs[index],
                        precipitationProbabilityPercent = precipitation[index],
                    )
                )
            }
        }

        return WeatherForecast(
            city = city,
            coordinates = coordinates,
            coordinateSource = coordinateSource,
            currentWeather = current,
            dailyForecast = days,
        )
    }
}
