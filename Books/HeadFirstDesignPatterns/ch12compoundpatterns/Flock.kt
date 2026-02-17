package ru.korobeynikov.ch12compoundpatterns

import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable
import ru.korobeynikov.ch12compoundpatterns.observable.Observer

class Flock : Quackable {

    val quackers = ArrayList<Quackable>()

    fun add(quacker: Quackable) {
        quackers.add(quacker)
    }

    override fun quack(): String {
        val history = StringBuilder()
        quackers.forEach { quacker ->
            history.append(quacker.quack())
        }
        history.append(notifyObservers())
        return history.toString()
    }

    override fun registerObserver(observer: Observer) {
        quackers.forEach { quacker ->
            quacker.registerObserver(observer)
        }
    }

    override fun notifyObservers(): String {
        return ""
    }
}