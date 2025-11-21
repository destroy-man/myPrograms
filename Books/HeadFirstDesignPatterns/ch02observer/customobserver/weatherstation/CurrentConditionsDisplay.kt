package ru.korobeynikov.ch02observer.customobserver.weatherstation

import android.util.Log
import ru.korobeynikov.ch02observer.customobserver.Observer
import ru.korobeynikov.ch02observer.customobserver.Subject

class CurrentConditionsDisplay(weatherData: Subject) : Observer, DisplayElement {

    private var temperature = 0f
    private var humidity = 0f

    init {
        weatherData.registerObserver(this)
    }

    override fun update(temp: Float, humidity: Float, pressure: Float) {
        this.temperature = temp
        this.humidity = humidity
        display()
    }

    override fun display() {
        Log.d("myLogs", "Current conditions: $temperature F degrees and $humidity% humidity")
    }
}