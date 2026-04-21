package ru.istu.kursgoldenringweather.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.istu.kursgoldenringweather.data.remote.dto.ApiNinjasCityDto

interface ApiNinjasService {

    @GET("v1/city")
    suspend fun findCity(
        @Query("name") name: String,
        @Query("country") countryCode: String = "RU",
    ): List<ApiNinjasCityDto>
}
