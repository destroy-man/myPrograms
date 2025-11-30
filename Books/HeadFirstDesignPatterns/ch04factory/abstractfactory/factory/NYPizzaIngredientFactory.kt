package ru.korobeynikov.ch04factory.abstractfactory.factory

import ru.korobeynikov.ch04factory.abstractfactory.ingredients.veggies.Garlic
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.veggies.Mushroom
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.veggies.Onion
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.veggies.RedPepper
import ru.korobeynikov.ch04factory.abstractfactory.nyingredients.FreshClams
import ru.korobeynikov.ch04factory.abstractfactory.nyingredients.MarinaraSauce
import ru.korobeynikov.ch04factory.abstractfactory.nyingredients.ReggianoCheese
import ru.korobeynikov.ch04factory.abstractfactory.nyingredients.SlicedPepperoni
import ru.korobeynikov.ch04factory.abstractfactory.nyingredients.ThinCrustDough

class NYPizzaIngredientFactory : PizzaIngredientFactory {

    override fun createDough() = ThinCrustDough()

    override fun createSauce() = MarinaraSauce()

    override fun createCheese() = ReggianoCheese()

    override fun createVeggies() = listOf(Garlic(), Onion(), Mushroom(), RedPepper())

    override fun createPepperoni() = SlicedPepperoni()

    override fun createClam() = FreshClams()
}