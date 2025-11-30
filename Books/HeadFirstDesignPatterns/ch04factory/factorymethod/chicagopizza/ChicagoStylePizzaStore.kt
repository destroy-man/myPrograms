package ru.korobeynikov.ch04factory.factorymethod.chicagopizza

import ru.korobeynikov.ch04factory.factorymethod.PizzaStore
import ru.korobeynikov.ch04factory.Pizza

class ChicagoStylePizzaStore : PizzaStore() {
    override fun createPizza(type: String): Pizza? {
        return when (type) {
            "cheese" -> ChicagoStyleCheesePizza()
            "greek" -> ChicagoStyleGreekPizza()
            "pepperoni" -> ChicagoStylePepperoniPizza()
            "clam" -> ChicagoStyleClamPizza()
            "veggie" -> ChicagoStyleVeggiePizza()
            else -> null
        }
    }
}