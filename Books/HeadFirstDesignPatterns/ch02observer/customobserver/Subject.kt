package ru.korobeynikov.ch02observer.customobserver

interface Subject {

    fun registerObserver(o: Observer)

    fun removeObserver(o: Observer)

    fun notifyObservers()
}