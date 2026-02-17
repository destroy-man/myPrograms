package ru.korobeynikov.ch12compoundpatterns.factory

import ru.korobeynikov.ch12compoundpatterns.ducks.DuckCall
import ru.korobeynikov.ch12compoundpatterns.ducks.MallardDuck
import ru.korobeynikov.ch12compoundpatterns.ducks.Quackable
import ru.korobeynikov.ch12compoundpatterns.ducks.RedheadDuck
import ru.korobeynikov.ch12compoundpatterns.ducks.RubberDuck

class DuckFactory : AbstractDuckFactory() {

    override fun createMallardDuck(): Quackable {
        return MallardDuck()
    }

    override fun createRedheadDuck(): Quackable {
        return RedheadDuck()
    }

    override fun createDuckCall(): Quackable {
        return DuckCall()
    }

    override fun createRubberDuck(): Quackable {
        return RubberDuck()
    }
}