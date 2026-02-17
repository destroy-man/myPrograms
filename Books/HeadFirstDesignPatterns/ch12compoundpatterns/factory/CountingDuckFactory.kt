package ru.korobeynikov.ch12compoundpatterns.factory

import ru.korobeynikov.ch12compoundpatterns.decorator.QuackCounter
import ru.korobeynikov.ch12compoundpatterns.ducks.DuckCall
import ru.korobeynikov.ch12compoundpatterns.ducks.MallardDuck
import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable
import ru.korobeynikov.ch12compoundpatterns.ducks.RedheadDuck
import ru.korobeynikov.ch12compoundpatterns.ducks.RubberDuck

class CountingDuckFactory : AbstractDuckFactory() {

    override fun createMallardDuck(): Quackable {
        return QuackCounter(MallardDuck())
    }

    override fun createRedheadDuck(): Quackable {
        return QuackCounter(RedheadDuck())
    }

    override fun createDuckCall(): Quackable {
        return QuackCounter(DuckCall())
    }

    override fun createRubberDuck(): Quackable {
        return QuackCounter(RubberDuck())
    }
}