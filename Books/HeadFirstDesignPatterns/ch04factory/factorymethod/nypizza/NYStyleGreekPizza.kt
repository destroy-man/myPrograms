package ru.korobeynikov.ch04factory.factorymethod.nypizza

import ru.korobeynikov.ch04factory.Pizza

class NYStyleGreekPizza : Pizza() {

    override var name = "NY Style Greek Pizza"
    override var dough = "NY Style Greek Dough"
    override var sauce = "NY Style Greek Sauce"

    init {
        toppings.add("NY Style Greek Topping")
    }
}