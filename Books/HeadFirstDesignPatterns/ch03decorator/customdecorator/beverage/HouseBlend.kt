package ru.korobeynikov.ch03decorator.customdecorator.beverage

class HouseBlend : Beverage() {

    override val description = "House Blend Coffee"

    override fun cost() = .89
}