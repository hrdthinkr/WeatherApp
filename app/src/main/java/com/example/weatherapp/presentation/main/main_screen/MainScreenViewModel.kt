package com.example.weatherapp.presentation.main.main_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Constants
import com.example.weatherapp.domain.repository.LocationRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
) : ViewModel() {
    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    init {
        getCurrentLocation()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getWeatherByCityName(city: String, apiKey: String) = viewModelScope.launch(Dispatchers.IO) {
        try {
            val response = weatherRepository.getWeatherByCityName(city, apiKey)
            _state.value.weatherResponse = response
            Log.d("MyLog", state.value.weatherResponse!!.toString())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getWeatherByLocation() =
        viewModelScope.launch(Dispatchers.IO) {
            _state.value = _state.value.copy(
                isLoading = true,
                error = null
            )
            locationRepository.getCurrentLocation()?.let { location ->
                val result = weatherRepository.getWeatherByLocation(location.latitude, location.longitude, apiKey = Constants.API_KEY)
                _state.value=_state.value.copy(
                    weatherResponse = result
                )
            } ?: kotlin.run {
                _state.value =  _state.value.copy(
                    isLoading = false,
                    error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                )
            }
        }

    fun getCurrentLocation() = viewModelScope.launch(Dispatchers.IO) {

        _state.value = _state.value.copy(
            currentLocation = locationRepository.getCurrentLocation()
        )
    }
}
