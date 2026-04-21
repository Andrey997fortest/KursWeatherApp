package ru.istu.kursgoldenringweather.di

import android.content.Context
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.istu.kursgoldenringweather.BuildConfig
import ru.istu.kursgoldenringweather.data.local.AppPreferencesRepository
import ru.istu.kursgoldenringweather.data.remote.ApiNinjasHeaderInterceptor
import ru.istu.kursgoldenringweather.data.remote.ApiNinjasService
import ru.istu.kursgoldenringweather.data.remote.OpenMeteoService
import ru.istu.kursgoldenringweather.data.repository.DefaultWeatherRepository
import ru.istu.kursgoldenringweather.data.repository.WeatherRepository
import ru.istu.kursgoldenringweather.data.source.GoldenRingCitySource

class AppContainer(context: Context) {

    private val gson = GsonBuilder().create()

    private val openMeteoClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BASIC
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
        )
        .build()

    private val apiNinjasClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiNinjasHeaderInterceptor(BuildConfig.API_NINJAS_KEY))
        .addInterceptor(
            HttpLoggingInterceptor().apply {
                level = if (BuildConfig.DEBUG) {
                    HttpLoggingInterceptor.Level.BASIC
                } else {
                    HttpLoggingInterceptor.Level.NONE
                }
            }
        )
        .build()

    private val openMeteoRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.open-meteo.com/")
        .client(openMeteoClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val apiNinjasRetrofit: Retrofit = Retrofit.Builder()
        .baseUrl("https://api.api-ninjas.com/")
        .client(apiNinjasClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    private val openMeteoService: OpenMeteoService =
        openMeteoRetrofit.create(OpenMeteoService::class.java)

    private val apiNinjasService: ApiNinjasService =
        apiNinjasRetrofit.create(ApiNinjasService::class.java)

    private val preferencesRepository = AppPreferencesRepository(context)

    val weatherRepository: WeatherRepository = DefaultWeatherRepository(
        openMeteoService = openMeteoService,
        apiNinjasService = apiNinjasService,
        citySource = GoldenRingCitySource,
        apiKey = BuildConfig.API_NINJAS_KEY,
    )

    val appPreferencesRepository: AppPreferencesRepository = preferencesRepository
}
