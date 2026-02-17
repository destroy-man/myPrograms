package ru.korobeynikov.ch12compoundpatterns.observable

interface QuackObservable {

    fun registerObserver(observer: Observer)

    fun notifyObservers(): String
}