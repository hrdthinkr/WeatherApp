package com.example.weatherapp.presentation.main.main_screen

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.common.Constants
import com.example.weatherapp.domain.model.ForecastType
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.PermissionStatus
import com.google.accompanist.permissions.rememberPermissionState
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MainScreen(
    modifier: Modifier=Modifier,
    viewModel: MainScreenViewModel,
){
    val state=viewModel.state.value
    viewModel.getCurrentLocation()
    viewModel.getWeatherByLocation()
    MainScreenContent(
        state = state,
        modifier=modifier,
        onMenuClick = {},
        onCityNameChanged = viewModel::getWeatherByCityName,
    )
}
@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MainScreenContent(
    state: MainScreenState,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    onCityNameChanged:(String,String)->Unit,
) {
    val isFineLocationPermissionsGranted =
        rememberPermissionState(Manifest.permission.ACCESS_FINE_LOCATION)
    val isCoarseLocationPermissionsGranted =
        rememberPermissionState(Manifest.permission.ACCESS_COARSE_LOCATION)
    val isLocationPermissionsGranted =
        isFineLocationPermissionsGranted.status == PermissionStatus.Granted &&
                isCoarseLocationPermissionsGranted.status == PermissionStatus.Granted

    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")
    val current = LocalDateTime.now().format(formatter)
    var cityNameValue by remember { mutableStateOf("") }

    val normalFontSize = 25.sp
    val smallFontSize = 15.sp

    val forecastTypesList = listOf(
        ForecastType.HOURLY,
        ForecastType.TOMORROW,
        ForecastType.WEEK,
    )
    val currentCityInfo = state.weatherResponse

    if (currentCityInfo != null) {
        if(currentCityInfo.name.isNotBlank()) {
            Box(
                modifier = modifier
                    .fillMaxSize()
            ) {
                if(state.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                Image(
                    modifier = Modifier.fillMaxSize(),
                    painter = painterResource(id = R.drawable.background),
                    contentScale = ContentScale.FillBounds,
                    contentDescription = ""
                )
                Column(
                    modifier = Modifier
                        .padding(25.dp)
                        .align(Alignment.TopStart),
                ) {
                    Text(
                        text = currentCityInfo.name + ",",
                        color = Color.White,
                        fontSize = normalFontSize,
                        fontWeight = FontWeight.W300
                    )

                    Spacer(modifier = Modifier.height(7.dp))

                    Text(
                        text = currentCityInfo.sys.country,
                        color = Color.White,
                        fontSize = normalFontSize,
                        fontWeight = FontWeight.W300
                    )

                    Spacer(modifier = Modifier.height(5.dp))

                    Text(
                        modifier = Modifier.alpha(0.6f),
                        text = current,
                        color = Color.White,
                    )
                }
                Icon(
                    modifier = Modifier
                        .padding(20.dp)
                        .clickable { onMenuClick() }
                        .size(30.dp)
                        .align(Alignment.TopEnd),
                    imageVector = Icons.Outlined.Menu,
                    tint = Color.White,
                    contentDescription = "menu",
                )
                Column(
                    modifier = Modifier
                        .align(Alignment.Center)
                        .padding(bottom = 150.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(90.dp),
                            model = currentCityInfo.weather[0].icon,
                            contentDescription = "",
                        )

                        Spacer(modifier = Modifier.width(20.dp))

                        Text(
                            text = currentCityInfo.main.temp.minus(273).toInt().toString(),
                            color = Color.White,
                            fontSize = 110.sp
                        )

                        Text(
                            modifier = Modifier.padding(bottom = 20.dp),
                            text = "°",
                            fontWeight = FontWeight.W300,
                            color = Color.White,
                            fontSize = 70.sp
                        )
                    }
                    Text(
                        text = currentCityInfo.weather[0].description,
                        color = Color.White,
                        fontSize = normalFontSize,
                        fontWeight = FontWeight.W300
                    )

                    Spacer(modifier = Modifier.height(50.dp))

                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Humidity " + currentCityInfo.main.humidity + "%",
                            color = Color.White,
                            fontSize = smallFontSize,
                            fontWeight = FontWeight.W300,
                        )

                        Text(
                            text = "Real Feel " + currentCityInfo.main.feels_like.minus(273).toInt() + "°",
                            color = Color.White,
                            fontSize = smallFontSize,
                            fontWeight = FontWeight.W300,
                        )

                        Text(
                            text = "Wind Speed " + currentCityInfo.wind.speed + " Mph",
                            color = Color.White,
                            fontSize = smallFontSize,
                            fontWeight = FontWeight.W300
                        )
                    }
                    Spacer(
                        modifier = Modifier
                            .height(60.dp)
                    )

                    Divider(
                        modifier = Modifier
                            .alpha(0.2f)
                            .padding(horizontal = 25.dp),
                        thickness = 2.dp,
                        color = Color.White,
                    )
                    ForecastMode(forecastTypesList = forecastTypesList)
                }
            }
        }else{
            Box(modifier = Modifier.fillMaxSize()){
                TextField(value = cityNameValue, onValueChange ={newText->
                    cityNameValue = newText

                    onCityNameChanged(cityNameValue, Constants.API_KEY) } )
            }
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()){
            TextField(value = cityNameValue, onValueChange ={newText->
                cityNameValue = newText

                onCityNameChanged(cityNameValue, Constants.API_KEY) } )
        }
    }

}

@Composable
private fun ForecastSearchScreen(
    modifier: Modifier = Modifier,
    state: MainScreenState,
) {
    var city by remember { mutableStateOf("") }
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        TextField(value = city, onValueChange = { city = it })
    }
}

@Composable
private fun ForecastMode(
    modifier: Modifier = Modifier,
    forecastTypesList: List<ForecastType>,
    selected: Int = 0,
) {
    var selectedItemIndex by rememberSaveable {
        mutableIntStateOf(selected)
    }

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(35.dp)
    ) {
        forecastTypesList.forEachIndexed { index, current ->
            val isSelected = selectedItemIndex == index
            val contentAlpha = if (isSelected) 1.0f else 0.7f
            Text(
                modifier = Modifier
                    .alpha(contentAlpha)
                    .clickable { selectedItemIndex = index }
                    .padding(vertical = 30.dp),
                text = stringResource(id = current.textId),
                color = Color.White,
                fontSize = 15.sp,
                fontWeight = FontWeight.W300,
            )
        }
    }
    when (selectedItemIndex) {
        0 -> {

        }

        1 -> {

        }

        2 -> {

        }
    }
}

@Composable
@Preview
private fun ForecastModePreview() {
    val forecastTypesList = listOf(
        ForecastType.HOURLY,
        ForecastType.TOMORROW,
        ForecastType.WEEK,
    )
    ForecastMode(forecastTypesList = forecastTypesList)
}

@Composable
@Preview
private fun MainScreenPreview() {

}