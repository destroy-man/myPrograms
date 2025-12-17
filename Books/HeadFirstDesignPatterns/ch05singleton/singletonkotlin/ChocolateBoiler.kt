package ru.korobeynikov.ch05singleton.singletonkotlin

//Компонент object из Kotlin частный случай реализации паттерна Синглтон
object ChocolateBoiler {

    private var empty = true
    private var boiled = false

    fun fill() {
        if (isEmpty()) {
            empty = false
            boiled = false
        }
    }

    fun drain() {
        if (!isEmpty() && isBoiled()) empty = true
    }

    fun boil() {
        if (!isEmpty() && !isBoiled()) boiled = true
    }

    fun isEmpty() = empty

    fun isBoiled() = boiled
}