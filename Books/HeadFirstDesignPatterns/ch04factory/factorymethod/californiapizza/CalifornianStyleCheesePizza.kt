package ru.korobeynikov.ch04factory.factorymethod.californiapizza

import ru.korobeynikov.ch04factory.Pizza

class CalifornianStyleCheesePizza : Pizza() {

    override var name = "Californian Style Cheese Pizza"
    override var dough = "Californian Style Cheese Dough"
    override var sauce = "Californian Style Cheese Sauce"

    init {
        toppings.add("Californian Style Cheese Topping")
    }
}