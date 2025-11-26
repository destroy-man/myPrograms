package ru.korobeynikov.ch03decorator.customdecorator.beverage

class Decaf : Beverage() {

    override val description = "Decaf"

    override fun cost() = 1.05
}