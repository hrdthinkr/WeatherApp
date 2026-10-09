package com.example.weatherapp.presentation.main.main_screen

import android.Manifest
import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import com.example.weatherapp.ui.theme.Backgroundd
import com.example.weatherapp.ui.theme.Panel
import com.example.weatherapp.ui.theme.HourCard
import com.example.weatherapp.ui.theme.HourCardBorder
import com.example.weatherapp.ui.theme.White
import com.example.weatherapp.ui.theme.SecondaryText
import com.example.weatherapp.ui.theme.Divider
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Menu
import androidx.compose.material3.Button
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
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

    val forecastTypesList = listOf(
        ForecastType.HOURLY,
        ForecastType.TOMORROW,
        ForecastType.WEEK
    )

    LaunchedEffect(locationPermissions.allPermissionsGranted) {
        if (
            locationPermissions.allPermissionsGranted &&
            state.currentLocation == null
        ) {
            getCurrentLocation()
        }
    }

    LaunchedEffect(Unit) {
        locationPermissions.launchMultiplePermissionRequest()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Backgroundd)
    ) {
        state.forecast?.let { data ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 24.dp, vertical = 28.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top
                ) {
                    Column(
                        modifier = Modifier.clickable {
                            onChangeLocationClick()
                        }
                    ) {
                        Text(
                            text = "${data.location.name},",
                            color = SecondaryText,
                            fontSize = 27.sp,
                            fontWeight = FontWeight.SemiBold
                        )

                        Text(
                            text = data.location.country.orEmpty(),
                            color = SecondaryText,
                            fontSize = 19.sp
                        )

                        Spacer(Modifier.height(10.dp))

                        Text(
                            text = current,
                            color = SecondaryText,
                            fontSize = 14.sp
                        )
                    }

                    Icon(
                        imageVector = Icons.Outlined.Menu,
                        contentDescription = "Menu",
                        tint = White,
                        modifier = Modifier
                            .padding(top = 6.dp)
                            .size(28.dp)
                            .clickable { onMenuClick() }
                    )
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .weight(1f),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        AsyncImage(
                            modifier = Modifier.size(140.dp),
                            model = "https:${data.current.condition.icon}",
                            contentDescription = data.current.condition.text
                        )

                        Text(
                            text = data.current.temp_c.roundToInt().toString(),
                            color = White,
                            fontWeight = FontWeight.Light,
                            fontSize = 80.sp
                        )

                        Text(
                            modifier = Modifier.padding(bottom = 20.dp),
                            text = "°",
                            fontWeight = FontWeight.Light,
                            color = White,
                            fontSize = 70.sp
                        )
                    }

                    Text(
                        text = data.current.condition.text,
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Medium
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    SmallRowWithIcon(
                        iconId = R.drawable.humidity,
                        title = "Humidity",
                        value = "${data.current.humidity} %"
                    )

                    MetricDivider()

                    SmallRowWithIcon(
                        iconId = R.drawable.temperature,
                        title = "Real Feel",
                        value = "${data.current.feelslike_c.roundToInt()}°",
                        iconSize = 20
                    )

                    MetricDivider()

                    SmallRowWithIcon(
                        iconId = R.drawable.wind,
                        title = "Wind Speed",
                        value = "${data.current.wind_mph} Mph",
                        iconSize = 20
                    )
                }

                Spacer(Modifier.height(22.dp))

                Divider(
                    color = Color.LightGray,
                    thickness = 1.dp
                )

                Spacer(Modifier.height(18.dp))

                ForecastMode(
                    forecastTypesList = forecastTypesList,
                    onHourlyForecastSelected = {
                        HourlyWeatherCardsRow(
                            list = data.forecast.forecastday[0].hour
                        )
                    },
                    onTomorrowForecastSelected = {
                        if (data.forecast.forecastday.size > 1) {
                            HourlyWeatherCardsRow(
                                list = data.forecast.forecastday[1].hour
                            )
                        }
                    },
                    onWeekForecastSelected = onWeekButtonClick
                )
            }
        }

        if (state.isLoading) {
            LoadingOverlay()
        }

        if (state.error != null) {
            PopupErrorView(message = state.error)
        }
    }
}


@Composable
private fun SmallRowWithIcon(
    modifier: Modifier = Modifier,
    iconId: Int,
    title: String,
    value: String,
    iconSize: Int = 23,
) {
    Column(modifier = modifier) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                modifier = Modifier.size(iconSize.dp),
                tint = SecondaryText,
                painter = painterResource(id = iconId),
                contentDescription = null
            )

            Spacer(Modifier.width(6.dp))

            Text(
                text = title,
                fontSize = 11.sp,
                color =SecondaryText,
                maxLines = 1
            )
        }

        Spacer(Modifier.height(5.dp))

        Text(
            text = value,
            fontSize = 14.sp,
            color = Color.White,
            fontWeight = FontWeight.Medium,
            maxLines = 1
        )
    }
}

@Composable
private fun MetricDivider() {
    Box(
        modifier = Modifier
            .width(1.dp)
            .height(40.dp)
            .background(Divider)//Divider
    )
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

    Column(
        modifier = modifier
            .background(
                color = Color.White,
                shape = RoundedCornerShape(26.dp)
            )
            .padding(vertical = 16.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            forecastTypesList.forEachIndexed { index, type ->
                val isSelected = selectedItemIndex == index

                Column(
                    modifier = Modifier
                        .clickable {
                            if (index == 2) {
                                onWeekForecastSelected()
                            } else {
                                selectedItemIndex = index
                            }
                        }
                        .padding(vertical = 5.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = stringResource(type.textId),
                        color = if (isSelected) {
                            White
                        } else {
                            SecondaryText
                        },
                        fontSize = 15.sp,
                        fontWeight = if (isSelected) {
                            FontWeight.SemiBold
                        } else {
                            FontWeight.Normal
                        }
                    )

                    Spacer(Modifier.height(8.dp))

                    Box(
                        modifier = Modifier
                            .width(48.dp)
                            .height(3.dp)
                            .background(
                                color = if (isSelected) {
                                    Color.White
                                } else {
                                    Color.Transparent
                                },
                                shape = RoundedCornerShape(50)
                            )
                    )
                }
            }
        }

        Spacer(Modifier.height(14.dp))

        when (selectedItemIndex) {
            0 -> onHourlyForecastSelected()
            1 -> onTomorrowForecastSelected()
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
