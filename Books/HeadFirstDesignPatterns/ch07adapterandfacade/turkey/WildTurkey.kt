package ru.korobeynikov.ch07adapterandfacade.turkey

class WildTurkey : Turkey {

    override fun gobble(): String {
        return "Gobble gobble"
    }

    override fun fly(): String {
        return "I'm flying a short distance"
    }
}