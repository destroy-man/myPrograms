package ru.korobeynikov.ch04factory.abstractfactory.pizza

import ru.korobeynikov.ch04factory.abstractfactory.factory.PizzaIngredientFactory

class ClamPizza(private val ingredientFactory: PizzaIngredientFactory) : Pizza() {
    override fun prepare() {
        history.append("Preparing $name\n")
        dough = ingredientFactory.createDough()
        sauce = ingredientFactory.createSauce()
        cheese = ingredientFactory.createCheese()
        clam = ingredientFactory.createClam()
    }
}