package ru.korobeynikov.ch12compoundpatterns.decorator

import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable
import ru.korobeynikov.ch12compoundpatterns.observable.Observer

class QuackCounter(private val duck: Quackable) : Quackable {

    override fun quack(): String {
        numberOfQuacks++
        return duck.quack()
    }

    override fun registerObserver(observer: Observer) {
        duck.registerObserver(observer)
    }

    override fun notifyObservers(): String {
        return duck.notifyObservers()
    }

    override fun toString(): String {
        return duck.toString()
    }

    companion object {

        var numberOfQuacks = 0

        fun getQuacks(): Int {
            return numberOfQuacks
        }
    }
}