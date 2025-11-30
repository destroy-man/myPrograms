package ru.korobeynikov.ch04factory.factorymethod.nypizza

import ru.korobeynikov.ch04factory.Pizza

class NYStyleCheesePizza : Pizza() {

    override var name = "NY Style Sauce and Cheese Pizza"
    override var dough = "Thin Crust Dough"
    override var sauce = "Marinara Sauce"

    init {
        toppings.add("Grated Reggiano Cheese")
    }
}