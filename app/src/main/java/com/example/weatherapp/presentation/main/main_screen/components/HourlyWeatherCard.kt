package com.example.weatherapp.presentation.main.main_screen.components

import androidx.compose.foundation.Image
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.weatherapp.R
import com.example.weatherapp.domain.model.WeatherModel

@Composable
fun HourlyWeatherCard(
    modifier: Modifier = Modifier,
    weatherModel: WeatherModel,
    color: Int = R.color.blue_800,
) {
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
                text = weatherModel.time,
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
                    model = weatherModel.iconUrl,
                    contentDescription = "",
                )
                Text(
                    text = weatherModel.humidity+" %",
                    color = Color.White,
                    fontSize = 6.sp,
                    modifier = Modifier
                        .alpha(0.7f)
                        .padding(vertical = 3.dp)
                )
            }
            Text(
                text = weatherModel.temperature+"°",
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