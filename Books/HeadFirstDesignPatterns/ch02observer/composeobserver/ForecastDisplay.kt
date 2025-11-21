package ru.korobeynikov.ch02observer.composeobserver

class ForecastDisplay(weatherData: WeatherData) : DisplayElement {

    private var temperature = weatherData.temperature

    override fun display(): String {
        val message = when {
            temperature < 80 -> "More of the same"
            temperature > 80 -> "Watch out for cooler, rainy weather"
            else -> "Improving weather on the way!"
        }
        return "Forecast: $message"
    }
}