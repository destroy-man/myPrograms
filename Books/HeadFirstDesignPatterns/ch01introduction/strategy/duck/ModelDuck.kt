package ru.korobeynikov.ch01introduction.strategy.duck

import ru.korobeynikov.ch01introduction.strategy.fly.FlyBehavior
import ru.korobeynikov.ch01introduction.strategy.fly.FlyNoWay
import ru.korobeynikov.ch01introduction.strategy.quack.Quack
import ru.korobeynikov.ch01introduction.strategy.quack.QuackBehavior

class ModelDuck(
    override var flyBehavior: FlyBehavior = FlyNoWay(),
    override var quackBehavior: QuackBehavior = Quack()
) : Duck(flyBehavior, quackBehavior) {
    override fun display() = "I'm a model duck"
}