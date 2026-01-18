package ru.korobeynikov.ch08templatemethod.withhook

class CoffeeWithHook : CaffeineBeverageWithHook() {

    override fun brew(): String {
        return "Dripping Coffee through filter"
    }

    override fun addCondiments(): String {
        return "Adding Sugar and Milk"
    }

    override fun customerWantsCondiments(answer: String): Boolean {
        return answer.lowercase().startsWith("y")
    }
}