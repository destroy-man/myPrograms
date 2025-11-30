package ru.korobeynikov.ch04factory.simplefactory.pizza

import ru.korobeynikov.ch04factory.Pizza

class PepperoniPizza : Pizza() {

    override var name = "Pepperoni Pizza"
    override var dough = "Pepperoni Dough"
    override var sauce = "Pepperoni Sauce"

    init {
        toppings.add("Pepperoni Topping")
    }
}