package ru.korobeynikov.ch04factory.abstractfactory.factory

import ru.korobeynikov.ch04factory.abstractfactory.chicagoingredients.FrozenClams
import ru.korobeynikov.ch04factory.abstractfactory.chicagoingredients.MozzarellaCheese
import ru.korobeynikov.ch04factory.abstractfactory.chicagoingredients.PlumTomatoSauce
import ru.korobeynikov.ch04factory.abstractfactory.chicagoingredients.ThickCrustDough
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.veggies.BlackOlives
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.veggies.EggPlant
import ru.korobeynikov.ch04factory.abstractfactory.ingredients.veggies.Spinach
import ru.korobeynikov.ch04factory.abstractfactory.nyingredients.SlicedPepperoni

class ChicagoPizzaIngredientFactory : PizzaIngredientFactory {

    override fun createDough() = ThickCrustDough()

    override fun createSauce() = PlumTomatoSauce()

    override fun createCheese() = MozzarellaCheese()

    override fun createVeggies() = listOf(EggPlant(), Spinach(), BlackOlives())

    override fun createPepperoni() = SlicedPepperoni()

    override fun createClam() = FrozenClams()
}