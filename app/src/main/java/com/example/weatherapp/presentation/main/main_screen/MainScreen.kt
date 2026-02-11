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
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.common.Constants
import com.example.weatherapp.domain.model.ForecastType
import com.example.weatherapp.presentation.main.destinations.WeeklyForecastScreenDestination
import com.example.weatherapp.presentation.main.main_screen.components.HourlyWeatherCardsRow
import com.example.weatherapp.ui.theme.component.LoadingOverlay
import com.example.weatherapp.ui.theme.component.PopupErrorView
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
@Destination
@RootNavGraph(start = true)
fun MainScreen(
    navigator: DestinationsNavigator,
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value

    MainScreenContent(
        state = state,
        modifier = modifier,
        onMenuClick = {},
        getCurrentLocation = viewModel::getCurrentLocation,
        onChangeLocationClick = { },
        onWeekButtonClick = { navigator.navigate(direction = WeeklyForecastScreenDestination(id = 2)) }
    )
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun MainScreenContent(
    state: MainScreenState,
    modifier: Modifier = Modifier,
    onMenuClick: () -> Unit,
    getCurrentLocation: () -> Unit,
    onChangeLocationClick: () -> Unit,
    onWeekButtonClick: () -> Unit,
) {
    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION
        )
    )
    val formatter = DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
    val current = LocalDateTime.now().format(formatter)

    val normalFontSize = 18.sp
    val smallFontSize = 15.sp

    val forecastTypesList = listOf(
        ForecastType.HOURLY,
        ForecastType.TOMORROW,
        ForecastType.WEEK,
    )

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (locationPermissions.allPermissionsGranted &&
            state.currentLocation == null
        ) {
            getCurrentLocation()
        }
    }
    LaunchedEffect(Unit) {
        locationPermissions.launchMultiplePermissionRequest()
    }
    state.forecast?.let { data ->
        val cityName = data.location.name
        Box(
            modifier = modifier
                .fillMaxSize(),

        ) {
            if (state.isLoading) {
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
                    .padding(top=40.dp, start = 35.dp)
                    .clickable {
                        onChangeLocationClick()
                    }
                    .align(Alignment.TopStart),
            ) {
                Text(
                    text = "$cityName,",
                    color = Color.White,
                    fontSize = normalFontSize,
                    fontWeight = FontWeight.W300
                )
                data.location?.country.let {
                    if (it != null) {
                        Text(
                            text = it,
                            color = Color.White,
                            fontSize = normalFontSize,
                            fontWeight = FontWeight.W300
                        )
                    }
                }

                Spacer(modifier = Modifier.height(5.dp))

                Text(
                    modifier = Modifier.alpha(0.6f),
                    text = current,
                    color = Color.White,
                )
            }
            Icon(
                modifier = Modifier
                    .padding(top=35.dp,end=35.dp)
                    .clickable { onMenuClick() }
                    .size(25.dp)
                    .align(Alignment.TopEnd),
                imageVector = Icons.Outlined.Menu,
                tint = Color.White,
                contentDescription = "menu",
            )

            Column(
                modifier = Modifier
                    .align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Spacer(modifier = Modifier.weight(1.0f))

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    AsyncImage(
                        modifier = Modifier
                            .size(140.dp),
                        model = "https:${data.current.condition.icon}",
                        contentDescription = "Condition icon"
                    )

                    Text(
                        text = data.current.temp_c.roundToInt().toString(),
                        color = Color.White,
                        fontWeight = FontWeight.W300,
                        fontSize = 80.sp
                    )

                    Text(
                        modifier = Modifier.padding(bottom = 20.dp),
                        text = "°",
                        fontWeight = FontWeight.W300,
                        color = Color.White,
                        fontSize = 70.sp
                    )

                }
                Spacer(modifier = Modifier.weight(1.0f))

                Text(
                    text = data.current.condition.text,
                    color = Color.White,
                    fontSize = normalFontSize,
                    fontWeight = FontWeight.W300
                )

                Spacer(modifier = Modifier.weight(1f))

                Column(
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    SmallRowWithIcon(
                        iconId = R.drawable.humidity,
                        text = "Humidity " + data.current.humidity + "%",
                        fontSize = smallFontSize,
                    )

                    SmallRowWithIcon(
                        iconId = R.drawable.temperature,
                        iconSize = 20,
                        text = "Real Feel " + data.current.feelslike_c.toInt() + "°",
                        fontSize = smallFontSize,
                    )

                    SmallRowWithIcon(
                        iconId = R.drawable.wind,
                        iconSize = 20,
                        text = "Wind Speed " + data.current.wind_mph + " Mph",
                        fontSize = smallFontSize
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
                ForecastMode(
                    forecastTypesList = forecastTypesList,
                    onHourlyForecastSelected = {
                        HourlyWeatherCardsRow(list = data.forecast.forecastday[0].hour)
                    },
                    onTomorrowForecastSelected = {
                        HourlyWeatherCardsRow(list = data.forecast.forecastday[1].hour)
                    },
                    onWeekForecastSelected = {
                        onWeekButtonClick()
                    },
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
            }
        }
    }

    if (state.isLoading) {
        LoadingOverlay()
    }
    if (state.error != null) {
        PopupErrorView(message = state.error)
    }
}

@Composable
private fun SmallRowWithIcon(
    modifier: Modifier = Modifier,
    iconId: Int,
    text: String,
    iconSize:Int=23,
    fontSize: TextUnit,
) {
    Row(
        modifier = modifier,
    ) {
        Icon(
            modifier = Modifier.size(iconSize.dp),
            tint = Color.White,
            painter = painterResource(id = iconId), contentDescription = "icon"
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text(
            text = text,
            fontSize = fontSize,
            color = Color.White,
            fontWeight = FontWeight.W300
        )
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
@Destination
fun ChangeLocationScreen(
    modifier: Modifier = Modifier,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {
    val coroutineScope = rememberCoroutineScope()
    var value by remember { mutableStateOf("") }
    Box(modifier = modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.align(Alignment.Center)
        ) {
            TextField(
                value = value,
                onValueChange = {
                    value = it
                })
            Button(
                modifier = Modifier.width(120.dp),
                onClick = {
                    coroutineScope.launch {
                        viewModel.getWeatherByCityName(
                            city = value,
                            key = Constants.API_KEY
                        )

                    }
                }
            ) {
                Text(
                    text = "Change city",
                    fontSize = 24.sp,
                    color = Color.White
                )
            }
        }
    }
}

@Composable
private fun ForecastMode(
    modifier: Modifier = Modifier,
    forecastTypesList: List<ForecastType>,
    selected: Int = 0,
    onHourlyForecastSelected: @Composable () -> Unit,
    onTomorrowForecastSelected: @Composable () -> Unit,
    onWeekForecastSelected: () -> Unit,
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
            onHourlyForecastSelected()
        }

        1 -> {
            onTomorrowForecastSelected()
        }

        2 -> {
            onWeekForecastSelected()
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
    ForecastMode(
        forecastTypesList = forecastTypesList,
        onWeekForecastSelected = {},
        onTomorrowForecastSelected = {},
        onHourlyForecastSelected = {},
    )
}

@Composable
@Preview
private fun MainScreenPreview() {

}
