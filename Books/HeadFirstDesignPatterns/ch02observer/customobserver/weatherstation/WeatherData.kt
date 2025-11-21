package ru.korobeynikov.ch02observer.customobserver.weatherstation

import ru.korobeynikov.ch02observer.customobserver.Observer
import ru.korobeynikov.ch02observer.customobserver.Subject

class WeatherData() : Subject {

    private var observers = ArrayList<Observer>()
    private var temperature = 0f
    private var humidity = 0f
    private var pressure = 0f

    override fun registerObserver(o: Observer) {
        observers.add(o)
    }

    override fun removeObserver(o: Observer) {
        if (observers.contains(o)) {
            observers.remove(o)
        }
    }

    override fun notifyObservers() {
        observers.forEach { observer ->
            observer.update(temperature, humidity, pressure)
        }
    }

    fun measureChanged() {
        notifyObservers()
    }

    fun setMeasurements(temperature: Float, humidity: Float, pressure: Float) {
        this.temperature = temperature
        this.humidity = humidity
        this.pressure = pressure
        measureChanged()
    }
}