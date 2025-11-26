package ru.korobeynikov.ch03decorator.customdecorator.decorator

import ru.korobeynikov.ch03decorator.customdecorator.beverage.Beverage

class Milk(private val beverage: Beverage) : CondimentDecorator() {

    override val description = "${beverage.description}, Milk"

    override fun cost() = .1 + beverage.cost()
}