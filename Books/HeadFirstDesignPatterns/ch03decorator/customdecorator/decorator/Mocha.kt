package ru.korobeynikov.ch03decorator.customdecorator.decorator

import ru.korobeynikov.ch03decorator.customdecorator.beverage.Beverage

class Mocha(private val beverage: Beverage) : CondimentDecorator() {

    override val description = "${beverage.description}, Mocha"

    override fun cost() = .2 + beverage.cost()
}