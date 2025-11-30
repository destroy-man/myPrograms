package ru.korobeynikov.ch04factory.factorymethod.californiapizza

import ru.korobeynikov.ch04factory.Pizza

class CalifornianStyleGreekPizza : Pizza() {

    override var name = "Californian Style Greek Pizza"
    override var dough = "Californian Style Greek Dough"
    override var sauce = "Californian Style Greek Sauce"

    init {
        toppings.add("Californian Style Greek Topping")
    }
}