package ru.korobeynikov.ch04factory.factorymethod.nypizza

import ru.korobeynikov.ch04factory.Pizza

class NYStylePepperoniPizza : Pizza() {

    override var name = "NY Style Pepperoni Pizza"
    override var dough = "NY Style Pepperoni Dough"
    override var sauce = "NY Style Pepperoni Sauce"

    init {
        toppings.add("NY Style Pepperoni Topping")
    }
}