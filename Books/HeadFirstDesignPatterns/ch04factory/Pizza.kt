package ru.korobeynikov.ch04factory

abstract class Pizza {

    val history = StringBuilder()
    val toppings = ArrayList<String>()
    open lateinit var name: String
    open lateinit var dough: String
    open lateinit var sauce: String

    open fun prepare() {
        history.append("Preparing $name\n")
        history.append("Tossing dough $dough\n")
        history.append("Adding sauce $sauce\n")
        history.append("Adding toppings: \n")
        toppings.forEach { topping ->
            history.append("\t$topping\n")
        }
    }

    open fun bake() {
        history.append("Bake for 25 minutes at 350\n")
    }

    open fun cut() {
        history.append("Cutting the pizza into diagonal slices\n")
    }

    open fun box() {
        history.append("Place pizza in official PizzaStore box\n")
    }
}