package ru.korobeynikov.p09memoryleaksleakcanary

class Repository {

    interface Listener {
        fun onDataChanged(newData: String)
    }

    private val listeners = HashSet<Listener>()

    fun registerListener(listener: Listener) = listeners.add(listener)
}