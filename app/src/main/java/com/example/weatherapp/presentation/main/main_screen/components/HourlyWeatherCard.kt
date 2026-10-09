package com.example.weatherapp.presentation.main.main_screen.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.secondForecast.Hour
import kotlin.math.roundToInt

@Composable
fun HourlyWeatherCard(
    modifier: Modifier = Modifier,
    hour: Hour,
    color: Int = R.color.blue_1200,
) {
    hour?.let { data ->
        val time = data.time.substring(10)

        Box(
            modifier = modifier
                .width(75.dp)
                .fillMaxSize()
        ) {
            Text(
                text = time,
                color = Color.White,
                fontSize = 12.sp,
                modifier = Modifier
                    .padding(vertical = 13.dp)
                    .align(Alignment.TopCenter),
            )
            Column(
                modifier = Modifier.align(Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    modifier = Modifier.size(60.dp),
                    model = "https:${data.condition.icon}",
                    contentDescription = "Condition icon"
                )

                Text(
                    text = data.humidity.toString() + " %",
                    color = Color.White,
                    fontSize = 11.sp,
                    modifier = Modifier
                        .alpha(0.7f)

                )
            }
            Text(
                text = data.temp_c.roundToInt().toString() + "°",
                color = Color.White,
                fontSize = 14.sp,
                modifier = Modifier
                    .padding(bottom = 11.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }

}

@Composable
fun HourlyWeatherCardsRow(
    modifier: Modifier = Modifier,
    list: List<Hour>,
    color: Int = R.color.blue_1200,
) {
    Card(
        modifier = modifier
            .height(140.dp)
            .padding(horizontal = 10.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(color)
        )
    )
    {
        LazyRow(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(25.dp),
            content = {
                items(list) { item ->
                    HourlyWeatherCard(hour = item)
                }
            })
    }
}

@Preview
@Composable
private fun HourlyWeatherCardPreview() {

}