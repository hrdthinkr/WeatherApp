package com.example.weatherapp

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.weatherapp.presentation.main.main_screen.MainScreen
import com.example.weatherapp.presentation.main.main_screen.NavGraphs
import com.example.weatherapp.ui.theme.WeatherAppTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            WeatherAppTheme {
                DestinationsNavHost(navGraph = NavGraphs.root)
            }
        }
    }
}
