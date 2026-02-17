package ru.korobeynikov.ch12compoundpatterns.ducks

import ru.korobeynikov.ch12compoundpatterns.observable.Observable
import ru.korobeynikov.ch12compoundpatterns.observable.Observer

class RedheadDuck : Quackable {

    val observable = Observable(this)

    override fun quack(): String {
        val history = StringBuilder("Quack\n")
        history.append(notifyObservers())
        return history.toString()
    }

    override fun registerObserver(observer: Observer) {
        observable.registerObserver(observer)
    }

    override fun notifyObservers(): String {
        return observable.notifyObservers()
    }

    override fun toString(): String {
        return "Redhead Duck"
    }
}