package ru.korobeynikov.p07memoryleakswhatisit

class Repository {

    private val listeners: MutableSet<(String) -> Unit> = mutableSetOf()

    fun registerListener(listener: (String) -> Unit) = listeners.add(listener)
}