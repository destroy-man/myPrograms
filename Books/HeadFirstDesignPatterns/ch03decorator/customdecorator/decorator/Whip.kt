package ru.korobeynikov.ch03decorator.customdecorator.decorator

import ru.korobeynikov.ch03decorator.customdecorator.beverage.Beverage

class Whip(private val beverage: Beverage) : CondimentDecorator() {

    override val description = "${beverage.description}, Whip"

    override fun cost() = .1 + beverage.cost()
}