package com.example.weatherapp.presentation.main.weekly_forecast

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
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import com.example.weatherapp.R
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.weatherapp.presentation.main.destinations.MainScreenDestination
import com.example.weatherapp.presentation.main.main_screen.MainScreenState
import com.example.weatherapp.presentation.main.main_screen.MainScreenViewModel
import com.example.weatherapp.secondForecast.Forecastday
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.DestinationsNavigator
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.roundToInt

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Destination
@Composable
fun WeeklyForecastScreen(
    modifier: Modifier = Modifier,
    navigator: DestinationsNavigator,
    id: Int,
    viewModel: MainScreenViewModel = hiltViewModel(),
) {
    val state = viewModel.state.value
    WeeklyForecastScreenContent(
        modifier = modifier,
        state = state,
        onArrowBackButtonClick = { navigator.navigate(MainScreenDestination()) }
    )
    viewModel.navigator = navigator
}

@Composable
private fun WeeklyForecastScreenContent(
    modifier: Modifier = Modifier,
    state: MainScreenState,
    onArrowBackButtonClick: () -> Unit,
) {
    state.forecast?.let { data ->
        val forecastList = data.forecast.forecastday
        Box(modifier = Modifier.fillMaxSize()) {
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
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                ForecastTopBar(onArrowBackButtonClick = { onArrowBackButtonClick() })

                Spacer(modifier = Modifier.weight(1f))

                ForecastTitle(title = stringResource(id = R.string.week))

                Spacer(modifier = Modifier.weight(1f))

                WeeklyForecastColumn(forecastDayList = forecastList)

                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
private fun ForecastTitle(
    title: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth().padding(horizontal = 15.dp)
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.W500,
            fontSize = 35.sp,
            color = Color.White,
        )
    }
}

@Composable
private fun ForecastTopBar(
    modifier: Modifier = Modifier,
    onArrowBackButtonClick: () -> Unit,
) {
    val formatter = DateTimeFormatter.ofPattern("dd MMMM, HH:mm")
    val current = LocalDateTime.now().format(formatter)

    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Box(
            modifier=Modifier.fillMaxWidth()
        ) {
            Icon(
                modifier = Modifier
                    .padding(20.dp)
                    .clickable { onArrowBackButtonClick() }
                    .size(30.dp),
                imageVector = Icons.Outlined.ArrowBack,
                tint = Color.White,
                contentDescription = "back",
            )

            Text(
                text = current,
                fontSize = 13.sp,
                color=Color.White,
                modifier = Modifier
                    .alpha(0.6f)
                    .align(Alignment.Center),
            )
        }
    }
}

@Composable
private fun WeeklyForecastRow(
    modifier: Modifier = Modifier,
    forecastDay: Forecastday,
) {
    val date = forecastDay.date.substring(5, 10)
    val temp = forecastDay.day.maxtemp_c.roundToInt().toString()
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 15.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = date,
            color = Color.White,
        )

        Spacer(modifier = Modifier.weight(1f))

        AsyncImage(
            modifier = Modifier
                .size(50.dp),
            model = "https:${forecastDay.day.condition.icon}",
            contentDescription = "Condition icon"
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "$temp°",
            fontSize = 20.sp,
            color = Color.White,
        )
    }
}

@Composable
private fun WeeklyForecastColumn(
    modifier: Modifier = Modifier,
    forecastDayList: List<Forecastday>,
) {
    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(horizontal = 10.dp),
        content = {
            items(forecastDayList) { item ->
                WeeklyForecastRow(forecastDay = item)

                Divider(modifier = Modifier.alpha(0.6f))
            }
        })
}

@Composable
@Preview(showBackground = true)
private fun WeeklyForecastRowPreview() {
    //WeeklyForecastRow()
}
