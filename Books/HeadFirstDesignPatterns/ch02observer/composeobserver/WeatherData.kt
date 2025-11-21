package ru.korobeynikov.ch02observer.composeobserver

class WeatherData(
    var temperature: Float = 0f,
    var humidity: Float = 0f,
    var pressure: Float = 0f,
    var temperatureList: ArrayList<Float> = ArrayList(),
) {

    fun setMeasurements(
        temperature: Float,
        humidity: Float,
        pressure: Float,
        onChange: (WeatherData) -> Unit
    ) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        this.temperatureList += temperature
        onChange(this)
    }
}