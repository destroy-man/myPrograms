package ru.korobeynikov.ch04factory.factorymethod.californiapizza

import ru.korobeynikov.ch04factory.Pizza

class CalifornianStylePepperoniPizza : Pizza() {

    override var name = "Californian Style Pepperoni Pizza"
    override var dough = "Californian Style Pepperoni Dough"
    override var sauce = "Californian Style Pepperoni Sauce"

    init {
        toppings.add("Californian Style Pepperoni Topping")
    }
}