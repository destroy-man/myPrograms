package ru.korobeynikov.p02creatingandlaunchlocaltest.calc

class Calculator {

    fun add(a: Int, b: Int) = a + b

    fun subtract(a: Int, b: Int) = a - b

    fun multiply(a: Int, b: Int) = a * b

    fun divide(a: Int, b: Int): Int {
        return if (b != 0)
            a / b
        else {
            println("Divide by 0")
            0
        }
    }
}