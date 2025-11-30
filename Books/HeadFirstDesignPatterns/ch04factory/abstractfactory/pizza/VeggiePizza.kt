package ru.korobeynikov.ch04factory.abstractfactory.pizza

import ru.korobeynikov.ch04factory.abstractfactory.factory.PizzaIngredientFactory

class VeggiePizza(private val ingredientFactory: PizzaIngredientFactory) : Pizza() {
    override fun prepare() {
        history.append("Preparing $name\n")
        dough = ingredientFactory.createDough()
        sauce = ingredientFactory.createSauce()
        cheese = ingredientFactory.createCheese()
        veggies = ingredientFactory.createVeggies()
    }
}