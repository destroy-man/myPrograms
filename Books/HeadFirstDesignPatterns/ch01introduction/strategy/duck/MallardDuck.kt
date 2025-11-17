package ru.korobeynikov.ch01introduction.strategy.duck

import ru.korobeynikov.ch01introduction.strategy.fly.FlyBehavior
import ru.korobeynikov.ch01introduction.strategy.fly.FlyWithWings
import ru.korobeynikov.ch01introduction.strategy.quack.Quack
import ru.korobeynikov.ch01introduction.strategy.quack.QuackBehavior

class MallardDuck(
    override var flyBehavior: FlyBehavior = FlyWithWings(),
    override var quackBehavior: QuackBehavior = Quack()
) : Duck(flyBehavior, quackBehavior) {
    override fun display() = "I'm a real Mallard duck"
}