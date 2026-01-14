package ru.korobeynikov.ch07adapterandfacade.hometheater

class Screen {

    fun up(): String {
        return "${toString()} going up"
    }

    fun down(): String {
        return "${toString()} going down"
    }

    override fun toString(): String {
        return "Theater Screen"
    }
}