package ru.korobeynikov.ch04factory.factorymethod.chicagopizza

import ru.korobeynikov.ch04factory.Pizza

class ChicagoStyleCheesePizza : Pizza() {

    override var name = "Chicago Style Deep Dish Cheese Pizza"
    override var dough = "Extra Thin Crust Dough"
    override var sauce = "Plum Tomato Sauce"

    init {
        toppings.add("Shredded Mozzarella Cheese")
    }

    override fun cut() {
        history.append("Cutting the pizza into square slices\n")
    }
}