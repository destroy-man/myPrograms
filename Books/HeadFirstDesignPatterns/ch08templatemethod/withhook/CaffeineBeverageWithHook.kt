package ru.korobeynikov.ch08templatemethod.withhook

abstract class CaffeineBeverageWithHook {

    fun prepareRecipe(answer: String): String {
        val history = StringBuilder()
        history.appendLine(boilWater())
        history.appendLine(brew())
        history.appendLine(pourInCup())
        if (customerWantsCondiments(answer)) {
            history.appendLine(addCondiments())
        }
        return history.toString()
    }

    abstract fun brew(): String

    abstract fun addCondiments(): String

    fun boilWater(): String {
        return "Boiling water"
    }

    fun pourInCup(): String {
        return "Pouring into cup"
    }

    open fun customerWantsCondiments(answer: String): Boolean {
        return true
    }
}