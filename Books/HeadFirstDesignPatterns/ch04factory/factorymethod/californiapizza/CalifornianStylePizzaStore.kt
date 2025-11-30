package ru.korobeynikov.ch04factory.factorymethod.californiapizza

import ru.korobeynikov.ch04factory.factorymethod.PizzaStore
import ru.korobeynikov.ch04factory.Pizza

class CalifornianStylePizzaStore : PizzaStore() {
    override fun createPizza(type: String): Pizza? {
        return when (type) {
            "cheese" -> CalifornianStyleCheesePizza()
            "greek" -> CalifornianStyleGreekPizza()
            "pepperoni" -> CalifornianStylePepperoniPizza()
            "clam" -> CalifornianStyleClamPizza()
            "veggie" -> CalifornianStyleVeggiePizza()
            else -> null
        }
    }
}