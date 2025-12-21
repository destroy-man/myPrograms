package ru.korobeynikov.ch06command.remotecontrol.garagedoor

class GarageDoor(private val location: String) {

    fun up(): String {
        return "$location garage door is open"
    }

    fun down(): String {
        return "$location garage door is close"
    }

    fun stop(): String {
        return "$location garage door action is stopped"
    }

    fun lightOn(): String {
        return "$location garage door light is on"
    }

    fun lightOff(): String {
        return "$location garage door light is off"
    }
}