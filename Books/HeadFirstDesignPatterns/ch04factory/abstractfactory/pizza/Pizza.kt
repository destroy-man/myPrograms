package ru.korobeynikov.ch04factory.abstractfactory.pizza

import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Cheese
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Clams
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Dough
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Pepperoni
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Sauce
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Veggies

abstract class Pizza {

    val history = StringBuilder()
    lateinit var name: String
    lateinit var dough: Dough
    lateinit var sauce: Sauce
    lateinit var veggies: List<Veggies>
    lateinit var cheese: Cheese
    lateinit var pepperoni: Pepperoni
    lateinit var clam: Clams

    abstract fun prepare()

    fun bake() {
        history.append("Bake for 25 minutes at 350\n")
    }

    fun cut() {
        history.append("Cutting the pizza into diagonal slices\n")
    }

    fun box() {
        history.append("Place pizza in official PizzaStore box\n")
    }

    override fun toString(): String {
        return history.toString()
    }
}