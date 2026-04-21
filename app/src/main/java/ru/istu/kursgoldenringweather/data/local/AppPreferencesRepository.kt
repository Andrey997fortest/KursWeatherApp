package ru.istu.kursgoldenringweather.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val PREFERENCES_NAME = "kurs_weather_preferences"
private val SELECTED_CITY_ID = stringPreferencesKey("selected_city_id")

private val Context.dataStore by preferencesDataStore(name = PREFERENCES_NAME)

class AppPreferencesRepository(private val context: Context) {

    val selectedCityId: Flow<String> = context.dataStore.data
        .map { preferences -> preferences[SELECTED_CITY_ID].orEmpty() }

    suspend fun saveSelectedCityId(cityId: String) {
        context.dataStore.edit { preferences ->
            preferences[SELECTED_CITY_ID] = cityId
        }
    }
}
