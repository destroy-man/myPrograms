package ru.korobeynikov.ch04factory.simplefactory.pizza

import ru.korobeynikov.ch04factory.Pizza

class CheesePizza : Pizza() {

    override var name = "Cheese Pizza"
    override var dough = "Cheese Dough"
    override var sauce = "Cheese Sauce"

    init {
        toppings.add("Cheese Topping")
    }
}