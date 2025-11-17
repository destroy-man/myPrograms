package ru.korobeynikov.ch01introduction.strategy.duck

import ru.korobeynikov.ch01introduction.strategy.fly.FlyBehavior
import ru.korobeynikov.ch01introduction.strategy.quack.QuackBehavior

abstract class Duck(open var flyBehavior: FlyBehavior, open var quackBehavior: QuackBehavior) {

    abstract fun display(): String

    fun performFly() = flyBehavior.fly()

    fun performQuack() = quackBehavior.quack()

    fun swim() = "All ducks float, even decoys!"
}