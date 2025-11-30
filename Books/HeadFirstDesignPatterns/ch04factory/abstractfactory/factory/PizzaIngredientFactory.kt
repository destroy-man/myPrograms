package ru.korobeynikov.ch04factory.abstractfactory.factory

import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Cheese
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Clams
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Dough
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Pepperoni
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Sauce
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.Veggies

interface PizzaIngredientFactory {

    fun createDough(): Dough

    fun createSauce(): Sauce

    fun createCheese(): Cheese

    fun createVeggies(): List<Veggies>

    fun createPepperoni(): Pepperoni

    fun createClam(): Clams
}