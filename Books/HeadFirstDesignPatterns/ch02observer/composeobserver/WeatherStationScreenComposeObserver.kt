package ru.korobeynikov.ch02observer.composeobserver

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

@Composable
fun WeatherStationScreenComposeObserver() {
    //Паттерн наблюдатель Реализация с отображением на экране
    var weatherData by remember { mutableStateOf(WeatherData()) }

    val currentDisplay = CurrentConditionsDisplay(weatherData)
    val statisticsDisplay = StatisticsDisplay(weatherData)
    val forecastDisplay = ForecastDisplay(weatherData)

    Column {
        Text(text = currentDisplay.display())
        Text(text = statisticsDisplay.display())
        Text(text = forecastDisplay.display())
        Button(onClick = {
            weatherData.setMeasurements(
                weatherData.temperature + 10,
                weatherData.humidity + 10,
                weatherData.pressure + 10
            ) { weatherDataNew ->
                weatherData = WeatherData(
                    weatherDataNew.temperature,
                    weatherDataNew.humidity,
                    weatherDataNew.pressure,
                    weatherDataNew.temperatureList
                )
            }
        }) {
            Text(text = "Increase")
        }
    }
}