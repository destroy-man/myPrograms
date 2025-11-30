package ru.korobeynikov.ch04factory.simplefactory

import ru.korobeynikov.ch04factory.simplefactory.pizza.CheesePizza
import ru.korobeynikov.ch04factory.simplefactory.pizza.ClamPizza
import ru.korobeynikov.ch04factory.simplefactory.pizza.GreekPizza
import ru.korobeynikov.ch04factory.simplefactory.pizza.PepperoniPizza
import ru.korobeynikov.ch04factory.Pizza
import ru.korobeynikov.ch04factory.simplefactory.pizza.VeggiePizza

class SimplePizzaFactory {
    fun createPizza(type: String): Pizza? {
        return when (type) {
            "cheese" -> CheesePizza()
            "greek" -> GreekPizza()
            "pepperoni" -> PepperoniPizza()
            "clam" -> ClamPizza()
            "veggie" -> VeggiePizza()
            else -> null
        }
    }
}