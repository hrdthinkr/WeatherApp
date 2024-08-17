package com.example.weatherapp.data.store

import android.content.Context
import android.net.Uri
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.weatherapp.domain.model.DayInfo
import com.example.weatherapp.domain.model.WeatherModel
import com.google.gson.GsonBuilder
import com.google.gson.TypeAdapter
import com.google.gson.stream.JsonReader
import com.google.gson.stream.JsonWriter
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
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
        private val KEY = stringPreferencesKey("city")
    }

    private val gson = GsonBuilder()
        .registerTypeAdapter(Uri::class.java, UriTypeAdapter())
        .create()

    private val cityFlow = MutableStateFlow<DayInfo?>(null)

    suspend fun save(dayInfo: DayInfo) {
        val jsonString = gson.toJson(dayInfo, WeatherModel::class.java)
        context.dataStore.edit { preferences ->
            preferences[KEY] = jsonString
        }
        cityFlow.value = dayInfo
    }

    fun load(): Flow<DayInfo?> =
        context.dataStore.data.map { preference ->
            val jsonString = preference[KEY] ?: ""
            if (jsonString.isNotBlank()) {
                gson.fromJson(jsonString, DayInfo::class.java)
            } else {
                null
            }
        }
    fun isEmpty(): Flow<Boolean> =
        context.dataStore.data.map { preference ->
            preference.contains(KEY)
        }
}
