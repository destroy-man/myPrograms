package ru.korobeynikov.ch03decorator.customdecorator.decorator

import ru.korobeynikov.ch03decorator.customdecorator.beverage.Beverage

abstract class CondimentDecorator : Beverage() {
    abstract override val description: String
}