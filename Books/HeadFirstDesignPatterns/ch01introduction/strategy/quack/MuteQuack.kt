package ru.korobeynikov.ch01introduction.strategy.quack

class MuteQuack : QuackBehavior {
    override fun quack() = "<< Silence >>"
}