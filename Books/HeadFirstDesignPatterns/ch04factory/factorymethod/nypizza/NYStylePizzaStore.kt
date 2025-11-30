package ru.korobeynikov.ch04factory.factorymethod.nypizza

import ru.korobeynikov.ch04factory.factorymethod.PizzaStore
import ru.korobeynikov.ch04factory.Pizza

class NYStylePizzaStore : PizzaStore() {
    override fun createPizza(type: String): Pizza? {
        return when (type) {
            "cheese" -> NYStyleCheesePizza()
            "greek" -> NYStyleGreekPizza()
            "pepperoni" -> NYStylePepperoniPizza()
            "clam" -> NYStyleClamPizza()
            "veggie" -> NYStyleVeggiePizza()
            else -> null
        }
    }
}