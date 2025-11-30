package ru.korobeynikov.ch04factory.factorymethod

import ru.korobeynikov.ch04factory.Pizza

abstract class PizzaStore {

    fun orderPizza(type: String): Pizza? {
        val pizza = createPizza(type)
        if (pizza != null) {
            pizza.prepare()
            pizza.bake()
            pizza.cut()
            pizza.box()
        }
        return pizza
    }

    protected abstract fun createPizza(type: String): Pizza?
}