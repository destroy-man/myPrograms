package ru.korobeynikov.ch04factory.factorymethod.chicagopizza

import ru.korobeynikov.ch04factory.Pizza

class ChicagoStylePepperoniPizza : Pizza() {

    override var name = "Chicago Style Pepperoni Pizza"
    override var dough = "Chicago Style Pepperoni Dough"
    override var sauce = "Chicago Style Pepperoni Sauce"

    init {
        toppings.add("Chicago Style Pepperoni Topping")
    }
}