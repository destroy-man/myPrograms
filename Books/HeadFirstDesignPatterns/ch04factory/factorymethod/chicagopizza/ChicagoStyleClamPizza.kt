package ru.korobeynikov.ch04factory.factorymethod.chicagopizza

import ru.korobeynikov.ch04factory.Pizza

class ChicagoStyleClamPizza : Pizza() {

    override var name = "Chicago Style Clam Pizza"
    override var dough = "Chicago Style Clam Dough"
    override var sauce = "Chicago Style Clam Sauce"

    init {
        toppings.add("Chicago Style Clam Topping")
    }
}