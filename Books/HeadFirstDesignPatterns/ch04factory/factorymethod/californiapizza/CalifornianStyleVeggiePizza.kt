package ru.korobeynikov.ch04factory.factorymethod.californiapizza

import ru.korobeynikov.ch04factory.Pizza

class CalifornianStyleVeggiePizza : Pizza() {

    override var name = "Californian Style Veggie Pizza"
    override var dough = "Californian Style Veggie Dough"
    override var sauce = "Californian Style Veggie Sauce"

    init {
        toppings.add("Californian Style Veggie Topping")
    }
}