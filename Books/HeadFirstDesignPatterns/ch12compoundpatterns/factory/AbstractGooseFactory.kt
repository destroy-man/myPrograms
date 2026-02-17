package ru.korobeynikov.ch12compoundpatterns.factory

import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable

abstract class AbstractGooseFactory {
    abstract fun createGoose(): Quackable
}