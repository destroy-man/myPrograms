package ru.korobeynikov.ch08templatemethod.withouthook

class Tea : CaffeineBeverage() {

    override fun brew(): String {
        return "Steeping the tea"
    }

    override fun addCondiments(): String {
        return "Adding Lemon"
    }
}