package ru.korobeynikov.ch01introduction.strategy.fly

class FlyRocketPowered : FlyBehavior {
    override fun fly() = "I'm flying with a rocket!"
}