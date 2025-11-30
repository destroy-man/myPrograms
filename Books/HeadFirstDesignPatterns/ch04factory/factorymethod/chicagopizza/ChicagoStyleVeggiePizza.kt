package ru.korobeynikov.ch04factory.factorymethod.chicagopizza

import ru.korobeynikov.ch04factory.Pizza

class ChicagoStyleVeggiePizza : Pizza() {

    override var name = "Chicago Style Veggie Pizza"
    override var dough = "Chicago Style Veggie Dough"
    override var sauce = "Chicago Style Veggie Sauce"

    init {
        toppings.add("Chicago Style Veggie Topping")
    }
}