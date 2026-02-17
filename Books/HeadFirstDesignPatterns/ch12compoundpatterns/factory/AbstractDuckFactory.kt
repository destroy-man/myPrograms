package ru.korobeynikov.ch12compoundpatterns.factory

import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable

abstract class AbstractDuckFactory {

    abstract fun createMallardDuck(): Quackable

    abstract fun createRedheadDuck(): Quackable

    abstract fun createDuckCall(): Quackable

    abstract fun createRubberDuck(): Quackable
}