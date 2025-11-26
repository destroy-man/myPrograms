package ru.korobeynikov.ch03decorator.kotlindecorator.beverage

class DarkRoast : Beverage() {

    override var description = "Dark Roast Coffee"

    override var cost: Double = .99
}