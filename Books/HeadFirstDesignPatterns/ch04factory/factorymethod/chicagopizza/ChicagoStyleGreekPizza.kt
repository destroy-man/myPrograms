package ru.korobeynikov.ch04factory.factorymethod.chicagopizza

import ru.korobeynikov.ch04factory.Pizza

class ChicagoStyleGreekPizza : Pizza() {

    override var name = "Chicago Style Greek Pizza"
    override var dough = "Chicago Style Greek Dough"
    override var sauce = "Chicago Style Greek Sauce"

    init {
        toppings.add("Chicago Style Greek Topping")
    }
}