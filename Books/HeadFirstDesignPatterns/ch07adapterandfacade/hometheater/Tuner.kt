package ru.korobeynikov.ch07adapterandfacade.hometheater

class Tuner {

    lateinit var amplifier: Amplifier

    fun on(): String {
        return "${toString()} is on"
    }

    fun off(): String {
        return "${toString()} is off"
    }

    fun setAm(): String {
        return "${toString()} set to am"
    }

    fun setFm(): String {
        return "${toString()} set to fm"
    }

    fun setFrequency(): String {
        return "${toString()} set to frequency"
    }

    override fun toString(): String {
        return "Tuner"
    }
}