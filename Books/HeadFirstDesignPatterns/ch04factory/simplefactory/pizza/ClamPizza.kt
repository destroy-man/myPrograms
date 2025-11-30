package ru.korobeynikov.ch04factory.simplefactory.pizza

import ru.korobeynikov.ch04factory.Pizza

class ClamPizza : Pizza() {

    override var name = "Clam Pizza"
    override var dough = "Clam Dough"
    override var sauce = "Clam Sauce"

    init {
        toppings.add("Clam Topping")
    }
}