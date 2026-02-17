package ru.korobeynikov.ch12compoundpatterns.goose

import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable
import ru.korobeynikov.ch12compoundpatterns.observable.Observable
import ru.korobeynikov.ch12compoundpatterns.observable.Observer

class GooseAdapter(private val goose: Goose) : Quackable {

    val observable = Observable(this)

    override fun quack(): String {
        val history = StringBuilder("${goose.honk()}\n")
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
        return "$goose pretending to be a Duck"
    }
}