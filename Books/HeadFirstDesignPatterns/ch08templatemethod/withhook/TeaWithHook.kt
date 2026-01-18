package ru.korobeynikov.ch08templatemethod.withhook

class TeaWithHook : CaffeineBeverageWithHook() {

    override fun brew(): String {
        return "Steeping the tea"
    }

    override fun addCondiments(): String {
        return "Adding Lemon"
    }

    override fun customerWantsCondiments(answer: String): Boolean {
        return answer.lowercase().startsWith("y")
    }
}