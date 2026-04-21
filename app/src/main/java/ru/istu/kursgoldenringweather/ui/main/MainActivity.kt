package ru.istu.kursgoldenringweather.ui.main

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import kotlin.math.roundToInt
import ru.istu.kursgoldenringweather.R
import ru.istu.kursgoldenringweather.WeatherApplication
import ru.istu.kursgoldenringweather.data.model.CoordinateSource
import ru.istu.kursgoldenringweather.databinding.ActivityMainBinding
import ru.istu.kursgoldenringweather.util.DateFormatter
import ru.istu.kursgoldenringweather.util.WeatherCodeMapper

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val cityAdapter = CityAdapter { city ->
        viewModel.onCitySelected(city.id)
    }

    private val dailyForecastAdapter = DailyForecastAdapter()

    private val viewModel: WeatherViewModel by viewModels {
        val container = (application as WeatherApplication).appContainer
        WeatherViewModelFactory(
            weatherRepository = container.weatherRepository,
            preferencesRepository = container.appPreferencesRepository,
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        setupLists()
        setupListeners()
        collectUiState()
    }

    private fun setupLists() = with(binding) {
        citiesRecyclerView.layoutManager =
            LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
        citiesRecyclerView.adapter = cityAdapter

        forecastRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity)
        forecastRecyclerView.adapter = dailyForecastAdapter
        forecastRecyclerView.isNestedScrollingEnabled = false
    }

    private fun setupListeners() = with(binding) {
        retryButton.setOnClickListener { viewModel.retry() }
        refreshButton.setOnClickListener { viewModel.retry() }
    }

    private fun collectUiState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    cityAdapter.update(state.cities, state.selectedCityId)
                    binding.loadingIndicator.isVisible = state.isLoading
                    binding.errorCard.isVisible = state.errorMessageRes != null && state.forecast == null
                    binding.contentContainer.isVisible = state.forecast != null

                    state.errorMessageRes?.let { binding.errorMessageText.setText(it) }

                    state.forecast?.let { forecast ->
                        binding.cityNameText.text = getString(forecast.city.nameResId)
                        binding.coordinatesText.text = getString(
                            R.string.coordinates_format,
                            forecast.coordinates.latitude,
                            forecast.coordinates.longitude,
                        )
                        binding.coordinatesSourceText.text = getString(
                            if (forecast.coordinateSource == CoordinateSource.API_NINJAS) {
                                R.string.coordinates_source_api
                            } else {
                                R.string.coordinates_source_fallback
                            }
                        )

                        val current = forecast.currentWeather
                        binding.currentTemperatureText.text = getString(
                            R.string.current_temperature_format,
                            current.temperatureCelsius.roundToInt(),
                        )
                        binding.currentConditionIconText.text = WeatherCodeMapper.icon(current.weatherCode)
                        binding.currentConditionText.text =
                            getString(WeatherCodeMapper.descriptionRes(current.weatherCode))
                        binding.windText.text = getString(
                            R.string.wind_speed_format,
                            current.windSpeedKmh.roundToInt(),
                        )
                        binding.updatedText.text = getString(
                            R.string.updated_at_format,
                            DateFormatter.formatDateTime(current.timeIso, this@MainActivity),
                        )

                        dailyForecastAdapter.update(forecast.dailyForecast)
                    }
                }
            }
        }
    }
}
