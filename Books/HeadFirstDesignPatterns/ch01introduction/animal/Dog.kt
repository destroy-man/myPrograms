package ru.korobeynikov.ch01introduction.animal

class Dog : Animal {

    override fun makeSound() = bark()

    fun bark() = "Гав-гав"
}