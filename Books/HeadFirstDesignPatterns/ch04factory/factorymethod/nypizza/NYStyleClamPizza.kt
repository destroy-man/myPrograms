package ru.korobeynikov.ch04factory.factorymethod.nypizza

import ru.korobeynikov.ch04factory.Pizza

class NYStyleClamPizza : Pizza() {

    override var name = "NY Style Clam Pizza"
    override var dough = "NY Style Clam Dough"
    override var sauce = "NY Style Clam Sauce"

    init {
        toppings.add("NY Style Clam Topping")
    }
}