package ru.korobeynikov.ch03decorator.kotlindecorator.beverage

class Decaf() : Beverage() {

    override var description = "Decaf"

    override var cost: Double = 1.05
}