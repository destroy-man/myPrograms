package ru.korobeynikov.ch03decorator.customdecorator.decorator

import ru.korobeynikov.ch03decorator.customdecorator.beverage.Beverage

class Soy(private val beverage: Beverage) : CondimentDecorator() {

    override val description = "${beverage.description}, Soy"

    override fun cost() = .15 + beverage.cost()
}