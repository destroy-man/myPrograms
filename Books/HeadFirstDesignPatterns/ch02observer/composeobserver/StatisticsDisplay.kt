package ru.korobeynikov.ch02observer.composeobserver

class StatisticsDisplay(weatherData: WeatherData) : DisplayElement {

    private var temperatureList = weatherData.temperatureList

    override fun display(): String {
        return if (temperatureList.isEmpty()) "Avg/Max/Min temperature = 0.0/0.0/0.0" else {
            val avg = temperatureList.average()
            val max = temperatureList.max()
            val min = temperatureList.min()
            "Avg/Max/Min temperature = $avg/$max/$min"
        }
    }
}