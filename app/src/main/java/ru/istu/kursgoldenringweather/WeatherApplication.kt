package ru.istu.kursgoldenringweather

import android.app.Application
import ru.istu.kursgoldenringweather.di.AppContainer

class WeatherApplication : Application() {
    lateinit var appContainer: AppContainer
        private set

    override fun onCreate() {
        super.onCreate()
        appContainer = AppContainer(applicationContext)
    }
}
