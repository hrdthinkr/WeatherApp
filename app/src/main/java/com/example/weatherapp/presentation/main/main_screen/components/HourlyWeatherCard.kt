package com.example.weatherapp.presentation.main.main_screen.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
import com.example.weatherapp.currentWeatherModel.WeatherResponse
import com.example.weatherapp.forecastWeatherModel.Forecast

@Composable
fun HourlyWeatherCard(
    modifier: Modifier = Modifier,
    weatherModel: Forecast,
    currentIndex:Int=0,
    color: Int = R.color.blue_800,
) {
    val date=weatherModel.list[0].date
    Card(
        modifier = modifier
            .width(65.dp)
            .height(110.dp),
        colors = CardDefaults.cardColors(
            containerColor = colorResource(color)
        )
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Text(
                text = date.substring(startIndex = 11, endIndex = 15),
                color = Color.White,
                fontSize = 9.sp,
                modifier = Modifier
                    .padding(vertical = 3.dp)
                    .align(Alignment.TopCenter),
            )
            Column(
                modifier=Modifier.align(Alignment.Center),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                AsyncImage(
                    modifier=Modifier.size(40.dp),
                    model = weatherModel.list[currentIndex].weather[0].icon,
                    contentDescription = "",
                )
                Text(
                    text = weatherModel.list[currentIndex].main.humidity.toString() + " %",
                    color = Color.White,
                    fontSize = 6.sp,
                    modifier = Modifier
                        .alpha(0.7f)
                        .padding(vertical = 3.dp)
                )
            }
            Text(
                text = weatherModel.list[currentIndex].main.temp.toString() +"°",
                color = Color.White,
                fontSize = 9.sp,
                modifier = Modifier
                    .padding(bottom = 9.dp)
                    .align(Alignment.BottomCenter),
            )
        }
    }
}

@Preview
@Composable
private fun HourlyWeatherCardPreview() {

}