package ru.korobeynikov.ch07adapterandfacade.hometheater

class TheaterLights {

    fun on(): String {
        return "${toString()} on"
    }

    fun off(): String {
        return "${toString()} off"
    }

    fun dim(value: Int): String {
        return "${toString()} dimming to $value%"
    }

    override fun toString(): String {
        return "Theater Ceiling Lights"
    }
}