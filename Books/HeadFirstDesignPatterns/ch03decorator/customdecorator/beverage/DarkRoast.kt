package ru.korobeynikov.ch03decorator.customdecorator.beverage

class DarkRoast : Beverage() {

    override val description = "Dark Roast Coffee"

    override fun cost() = .99
}