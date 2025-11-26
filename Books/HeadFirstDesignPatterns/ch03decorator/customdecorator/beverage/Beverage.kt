package ru.korobeynikov.ch03decorator.customdecorator.beverage

abstract class Beverage {

    open val description = "Unknown Beverage"

    abstract fun cost(): Double
}