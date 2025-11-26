package ru.korobeynikov.ch03decorator.kotlindecorator.beverage

abstract class Beverage() {

    open var description = "Unknown Beverage"

    abstract var cost: Double
}