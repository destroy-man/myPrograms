package ru.korobeynikov.ch02observer.customobserver

interface Observer {
    fun update(temp: Float, humidity: Float, pressure: Float)
}