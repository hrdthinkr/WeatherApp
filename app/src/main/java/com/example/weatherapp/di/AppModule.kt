package com.example.weatherapp.di

import android.app.Application
import com.example.weatherapp.common.Constants
import com.example.weatherapp.data.remote.WeatherApi
import com.example.weatherapp.data.repository.LocationRepositoryImpl
import com.example.weatherapp.data.repository.WeatherRepositoryImpl
import com.example.weatherapp.domain.repository.LocationRepository
import com.example.weatherapp.domain.repository.WeatherRepository
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideForecastApi(): WeatherApi {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(WeatherApi::class.java)
    }

    @Provides
    @Singleton
    fun provideWeatherRepository(api:WeatherApi):WeatherRepository{
        return WeatherRepositoryImpl(api)
    }

    @Provides
    @Singleton
    fun providesFusedLocationProviderClient(
        application: Application,
    ): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(application)
    }

    @Provides
    @Singleton
    fun providesLocationTracker(
        fusedLocationProviderClient: FusedLocationProviderClient,
        application: Application,
    ): LocationRepository = LocationRepositoryImpl(
        fusedLocationProviderClient = fusedLocationProviderClient,
        application = application
    )
}

