package ru.korobeynikov.ch12compoundpatterns.ducks

import ru.korobeynikov.ch12compoundpatterns.observable.QuackObservable

interface Quackable : QuackObservable {
    fun quack(): String
}