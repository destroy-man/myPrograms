package ru.korobeynikov.ch08templatemethod.withouthook

class Coffee : CaffeineBeverage() {

    override fun brew(): String {
        return "Dripping Coffee through filter"
    }

    override fun addCondiments(): String {
        return "Adding Sugar and Milk"
    }
}