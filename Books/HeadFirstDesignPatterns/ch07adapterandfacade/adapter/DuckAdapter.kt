package ru.korobeynikov.ch07adapterandfacade.adapter

import ru.korobeynikov.ch07adapterandfacade.duck.Duck
import ru.korobeynikov.ch07adapterandfacade.turkey.Turkey

class DuckAdapter(private val duck: Duck) : Turkey {

    override fun gobble(): String {
        return duck.quack()
    }

    override fun fly(): String {
        return "${duck.fly()} to 1/5"
    }
}