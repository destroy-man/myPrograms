package ru.korobeynikov.ch06command.simpleremotecontrol.garagedoor

class GarageDoor {

    fun up(): String {
        return "Garage Door is Open"
    }

    fun down(): String {
        return "Garage Door is Close"
    }

    fun stop(): String {
        return "Garage Door Action is Stopped"
    }

    fun lightOn(): String {
        return "Garage Door Light is On"
    }

    fun lightOff(): String {
        return "Garage Door Light is Off"
    }
}