package ru.korobeynikov.ch02observer.customobserver.weatherstation

import android.util.Log
import ru.korobeynikov.ch02observer.customobserver.Observer
import ru.korobeynikov.ch02observer.customobserver.Subject

class StatisticsDisplay(weatherData: Subject) : Observer, DisplayElement {

    private var temperatureList = ArrayList<Float>()

    init {
        weatherData.registerObserver(this)
    }

    override fun update(temp: Float, humidity: Float, pressure: Float) {
        temperatureList.add(temp)
        display()
    }

    override fun display() {
        val avg = temperatureList.average()
        val max = temperatureList.max()
        val min = temperatureList.min()
        Log.d("myLogs", "Avg/Max/Min temperature = $avg/$max/$min")
    }
}