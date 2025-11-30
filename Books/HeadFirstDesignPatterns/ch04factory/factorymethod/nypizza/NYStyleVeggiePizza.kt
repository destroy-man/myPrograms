package ru.korobeynikov.ch04factory.factorymethod.nypizza

import ru.korobeynikov.ch04factory.Pizza

class NYStyleVeggiePizza : Pizza() {

    override var name = "NY Style Veggie Pizza"
    override var dough = "NY Style Veggie Dough"
    override var sauce = "NY Style Veggie Sauce"

    init {
        toppings.add("NY Style Veggie Topping")
    }
}