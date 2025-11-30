package ru.korobeynikov.ch04factory.simplefactory.pizza

import ru.korobeynikov.ch04factory.Pizza

class GreekPizza : Pizza() {

    override var name = "Greek Pizza"
    override var dough = "Greek Dough"
    override var sauce = "Greek Sauce"

    init {
        toppings.add("Greek Topping")
    }
}