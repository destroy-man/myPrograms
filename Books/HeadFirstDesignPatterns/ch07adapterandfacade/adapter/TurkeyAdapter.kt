package ru.korobeynikov.ch07adapterandfacade.adapter

import ru.korobeynikov.ch07adapterandfacade.duck.Duck
import ru.korobeynikov.ch07adapterandfacade.turkey.Turkey

class TurkeyAdapter(private val turkey: Turkey) : Duck {

    override fun quack(): String {
        return turkey.gobble()
    }

    override fun fly(): String {
        val history = StringBuilder()
        repeat(5) {
            history.appendLine(turkey.fly())
        }
        return history.toString()
    }
}