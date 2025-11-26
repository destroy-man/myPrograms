package ru.korobeynikov.ch03decorator.customdecorator.beverage

class Espresso : Beverage() {

    override val description = "Espresso"

    override fun cost() = 1.99
}