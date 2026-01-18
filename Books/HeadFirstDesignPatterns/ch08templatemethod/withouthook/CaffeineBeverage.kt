package ru.korobeynikov.ch08templatemethod.withouthook

abstract class CaffeineBeverage {

    fun prepareRecipe(): String {
        val history = StringBuilder()
        history.appendLine(boilWater())
        history.appendLine(brew())
        history.appendLine(pourInCup())
        history.appendLine(addCondiments())
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
}