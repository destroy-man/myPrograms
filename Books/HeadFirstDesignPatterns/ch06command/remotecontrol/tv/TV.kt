package ru.korobeynikov.ch06command.remotecontrol.tv

class TV(private val location: String) {

    fun on(): String {
        return "$location tv is on"
    }

    fun off(): String {
        return "$location tv is off"
    }

    fun setInputChannel(): String {
        return "$location tv channel is set for DVD"
    }

    fun setVolume(volume: Int): String {
        return "$location tv volume set to $volume"
    }
}