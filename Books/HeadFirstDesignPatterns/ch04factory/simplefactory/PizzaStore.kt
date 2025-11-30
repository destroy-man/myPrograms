package ru.korobeynikov.ch04factory.simplefactory

import ru.korobeynikov.ch04factory.Pizza

class PizzaStore(private val factory: SimplePizzaFactory) {
    fun orderPizza(type: String): Pizza? {
        val pizza = factory.createPizza(type)
        if (pizza != null) {
            pizza.prepare()
            pizza.bake()
            pizza.cut()
            pizza.box()
        }
        return pizza
    }
}