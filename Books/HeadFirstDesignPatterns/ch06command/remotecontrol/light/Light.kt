package ru.korobeynikov.ch06command.remotecontrol.light

class Light(private val location: String) {

    fun on(): String {
        return "$location light is on"
    }

    fun off(): String {
        return "$location light is off"
    }
}