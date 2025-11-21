package ru.korobeynikov.ch02observer.composeobserver

class CurrentConditionsDisplay(weatherData: WeatherData) : DisplayElement {

    private var temperature = weatherData.temperature
    private var humidity = weatherData.humidity

    override fun display() = "Current conditions: $temperature F degrees and $humidity% humidity"
}