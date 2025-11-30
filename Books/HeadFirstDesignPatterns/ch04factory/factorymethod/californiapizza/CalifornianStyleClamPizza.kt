package ru.korobeynikov.ch04factory.factorymethod.californiapizza

import ru.korobeynikov.ch04factory.Pizza

class CalifornianStyleClamPizza : Pizza() {

    override var name = "Californian Style Clam Pizza"
    override var dough = "Californian Style Clam Dough"
    override var sauce = "Californian Style Clam Sauce"

    init {
        toppings.add("Californian Style Clam Topping")
    }
}