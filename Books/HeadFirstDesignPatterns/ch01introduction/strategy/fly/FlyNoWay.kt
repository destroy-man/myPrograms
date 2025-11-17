package ru.korobeynikov.ch01introduction.strategy.fly

class FlyNoWay : FlyBehavior {
    override fun fly() = "I can't fly"
}