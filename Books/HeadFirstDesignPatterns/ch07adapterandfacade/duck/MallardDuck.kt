package ru.korobeynikov.ch07adapterandfacade.duck

class MallardDuck : Duck {

    override fun quack(): String {
        return "Quack"
    }

    override fun fly(): String {
        return "I'm flying"
    }
}