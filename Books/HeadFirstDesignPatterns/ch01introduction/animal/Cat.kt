package ru.korobeynikov.ch01introduction.animal

class Cat : Animal {

    override fun makeSound() = meow()

    fun meow() = "Мяу"
}