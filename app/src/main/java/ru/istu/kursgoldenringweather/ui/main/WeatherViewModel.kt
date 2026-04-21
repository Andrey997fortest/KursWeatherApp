package ru.istu.kursgoldenringweather.ui.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.istu.kursgoldenringweather.R
import ru.istu.kursgoldenringweather.data.local.AppPreferencesRepository
import ru.istu.kursgoldenringweather.data.repository.WeatherRepository
import ru.istu.kursgoldenringweather.data.source.GoldenRingCitySource

class WeatherViewModel(
    private val weatherRepository: WeatherRepository,
    private val preferencesRepository: AppPreferencesRepository,
) : ViewModel() {

    private val _uiState = MutableStateFlow(WeatherUiState())
    val uiState: StateFlow<WeatherUiState> = _uiState.asStateFlow()

    private var loadJob: Job? = null

    init {
        restoreSelectionAndLoad()
    }

    fun onCitySelected(cityId: String) {
        if (cityId == _uiState.value.selectedCityId && _uiState.value.forecast != null) {
            return
        }
        loadForecast(cityId = cityId, persistSelection = true)
    }

    fun retry() {
        loadForecast(cityId = _uiState.value.selectedCityId, persistSelection = false)
    }

    private fun restoreSelectionAndLoad() {
        viewModelScope.launch {
            val savedCityId = preferencesRepository.selectedCityId.first()
            val initialCityId = GoldenRingCitySource.findById(savedCityId)?.id
                ?: GoldenRingCitySource.cities.first().id
            loadForecast(cityId = initialCityId, persistSelection = true)
        }
    }

    private fun loadForecast(cityId: String, persistSelection: Boolean) {
        loadJob?.cancel()
        loadJob = viewModelScope.launch {
            val previousForecast = _uiState.value.forecast
            _uiState.value = _uiState.value.copy(
                selectedCityId = cityId,
                isLoading = true,
                errorMessageRes = null,
            )

            if (persistSelection) {
                preferencesRepository.saveSelectedCityId(cityId)
            }

            runCatching { weatherRepository.getForecast(cityId) }
                .onSuccess { forecast ->
                    _uiState.value = _uiState.value.copy(
                        selectedCityId = cityId,
                        isLoading = false,
                        forecast = forecast,
                        errorMessageRes = null,
                    )
                }
                .onFailure {
                    _uiState.value = _uiState.value.copy(
                        selectedCityId = cityId,
                        isLoading = false,
                        forecast = previousForecast,
                        errorMessageRes = R.string.error_loading_weather,
                    )
                }
        }
    }
}
