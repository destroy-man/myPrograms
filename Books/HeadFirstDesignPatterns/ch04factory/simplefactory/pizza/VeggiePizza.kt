package ru.korobeynikov.ch04factory.simplefactory.pizza

import ru.korobeynikov.ch04factory.Pizza

class VeggiePizza : Pizza() {

    override var name = "Veggie Pizza"
    override var dough = "Veggie Dough"
    override var sauce = "Veggie Sauce"

    init {
        toppings.add("Veggie Topping")
    }
}