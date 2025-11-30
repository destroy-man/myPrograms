package ru.korobeynikov.ch04factory.abstractfactory

import ru.korobeynikov.ch04factory.abstractfactory.factory.NYPizzaIngredientFactory
import ru.korobeynikov.ch04factory.abstractfactory.pizza.CheesePizza
import ru.korobeynikov.ch04factory.abstractfactory.pizza.ClamPizza
import ru.korobeynikov.ch04factory.abstractfactory.pizza.PepperoniPizza
import ru.korobeynikov.ch04factory.abstractfactory.pizza.Pizza
import ru.korobeynikov.ch04factory.abstractfactory.pizza.VeggiePizza

class NYPizzaStore : PizzaStore() {
    override fun createPizza(type: String): Pizza? {
        val ingredientFactory = NYPizzaIngredientFactory()
        return when (type) {
            "cheese" -> CheesePizza(ingredientFactory).apply {
                name = "New York Style Cheese Pizza"
            }

            "veggie" -> VeggiePizza(ingredientFactory).apply {
                name = "New York Style Veggie Pizza"
            }

            "clam" -> ClamPizza(ingredientFactory).apply {
                name = "New York Style Clam Pizza"
            }

            "pepperoni" -> PepperoniPizza(ingredientFactory).apply {
                name = "New York Style Pepperoni Pizza"
            }

            else -> null
        }
    }
}