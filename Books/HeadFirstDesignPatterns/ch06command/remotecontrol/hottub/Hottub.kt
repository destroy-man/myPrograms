package ru.korobeynikov.ch06command.remotecontrol.hottub

class Hottub {

    var currentTemperature = 0

    fun on(): String {
        return "Hottub is heating to a steaming $currentTemperature degrees"
    }

    fun off(): String {
        return "Hottub is cooling to $currentTemperature degrees"
    }

    fun circulate(): String {
        return "Hottub is bubbling!"
    }

    fun jetsOn(): String {
        return "Hottub is jets on"
    }

    fun jetsOff(): String {
        return "Hottub is jets off"
    }

    fun setTemperature(temperature: Int) {
        currentTemperature = temperature
    }
}