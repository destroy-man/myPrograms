package ru.korobeynikov.ch02observer.customobserver.weatherstation

import android.util.Log
import ru.korobeynikov.ch02observer.customobserver.Observer

class ForecastDisplay(weatherData: WeatherData) : Observer, DisplayElement {

    private var temperature = 0f

    init {
        weatherData.registerObserver(this)
    }

    override fun update(temp: Float, humidity: Float, pressure: Float) {
        this.temperature = temp
        display()
    }

    override fun display() {
        val message = when {
            temperature < 80 -> "More of the same"
            temperature > 80 -> "Watch out for cooler, rainy weather"
            else -> "Improving weather on the way!"
        }
        Log.d("myLogs", "Forecast: $message")
    }
}