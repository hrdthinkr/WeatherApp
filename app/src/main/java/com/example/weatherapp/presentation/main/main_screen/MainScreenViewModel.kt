package com.example.weatherapp.presentation.main.main_screen

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.weatherapp.common.Constants
import com.example.weatherapp.common.Resource
import com.example.weatherapp.data.store.CityStore
import com.example.weatherapp.domain.repository.LocationRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.example.weatherapp.forecastWeatherModel.Forecast
import com.example.weatherapp.secondForecast.secondForecast
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository,
    private val locationRepository: LocationRepository,
    private val cityStore: CityStore,
) : ViewModel() {
    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    lateinit var navigator: DestinationsNavigator

    init {
        viewModelScope.launch {
            cityStore.loadWeather().collect { forecast ->
                _state.value = MainScreenState(
                    forecast = forecast,
                )
            }
        }
        getWeatherByLocation()
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getWeatherByCityName(key: String, city: String) = viewModelScope.launch(Dispatchers.Main) {
        when (val result = weatherRepository.getWeatherByCityName(key, city)) {
            is Resource.Success -> {
                _state.value = _state.value.copy(
                    forecast = result.data,
                    isLoading = false,
                    error = null,
                )
                if (result.data != null) {
                    cityStore.save(result.data)
                }

                Log.d("MyLog", "Message:" + state.value.forecast!!.toString())
            }

            is Resource.Error -> {
                _state.value = _state.value.copy(
                    forecast = null,
                    isLoading = false,
                    error = result.message
                )
            }

            is Resource.Loading -> {
                _state.value = _state.value.copy(
                    isLoading = true,
                )
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getWeatherByLocation() =
        viewModelScope.launch(Dispatchers.Main) {
            try {
                _state.value = _state.value.copy(
                    isLoading = true,
                    error = null
                )
                locationRepository.getCurrentLocation()?.let { location ->
                    when (val result = weatherRepository.getWeatherByLocation(
                        key = Constants.API_KEY,
                        lat = location.latitude,
                        lon = location.longitude,
                    )) {
                        is Resource.Success -> {
                            _state.value = _state.value.copy(
                                forecast = result.data,
                            )
                            Log.d(
                                "MyLog",
                                "Message:by location" + state.value.forecast!!.toString()
                            )
                            result.data?.let { saveWeather(it) }
                            Log.d("MyLog", "Message:" + state.value.forecast!!.toString())
                        }

                        is Resource.Error -> {
                            _state.value = _state.value.copy(
                                forecast = null,
                                isLoading = false,
                                error = result.message
                            )
                        }

                        is Resource.Loading -> {
                            _state.value = _state.value.copy(
                                isLoading = true,
                            )
                        }

                    }
                } ?: kotlin.run {
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = "Couldn't retrieve location. Make sure to grant permission and enable GPS."
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

    private fun saveWeather(forecast: secondForecast) {
        viewModelScope.launch {
            cityStore.save(forecast)
        }
    }

    fun getCurrentLocation() = viewModelScope.launch(Dispatchers.Main) {
        try {
            _state.value = _state.value.copy(
                currentLocation = locationRepository.getCurrentLocation()
            )
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}
