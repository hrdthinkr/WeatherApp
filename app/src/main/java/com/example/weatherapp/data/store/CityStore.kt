package com.example.weatherapp.data.store

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.forecastWeatherModel.Forecast
import com.example.weatherapp.secondForecast.secondForecast
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class UriTypeAdapter : TypeAdapter<Uri>() {
    override fun write(out: JsonWriter?, value: Uri?) {
        out?.value(value?.toString())
    }

    override fun read(input: JsonReader?): Uri? {
        return Uri.parse(input?.nextString())
    }
}

class CityStore @Inject constructor(@ApplicationContext private val context: Context) {
    companion object {
        private val Context.dataStore: DataStore<Preferences>
                by preferencesDataStore("CityStore")
        private val FORECAST_KEY = stringPreferencesKey("forecast")
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
        .create()


    suspend fun save(forecast: secondForecast) {
        val forecastJsonString = gson.toJson(forecast, secondForecast::class.java)
        context.dataStore.edit { preferences ->
            preferences[FORECAST_KEY]=forecastJsonString
        }
    }

    fun loadWeather(): Flow<secondForecast?> =
        context.dataStore.data.map { preference ->
            val weatherJsonString = preference[FORECAST_KEY] ?: null
            if (weatherJsonString!=null) {
                gson.fromJson(weatherJsonString, secondForecast::class.java)
            } else {
                null
            }
        }
    fun isEmpty(): Flow<Boolean> =
        context.dataStore.data.map { preference ->
            preference.contains(FORECAST_KEY)
        }
}
