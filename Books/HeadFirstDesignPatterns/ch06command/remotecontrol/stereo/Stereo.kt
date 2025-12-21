package ru.korobeynikov.ch06command.remotecontrol.stereo

class Stereo(private val location: String) {

    fun on(): String {
        return "$location stereo is on"
    }

    fun off(): String {
        return "$location stereo is off"
    }

    fun setCD(): String {
        return "$location stereo is set for CD input"
    }

    fun setDVD(): String {
        return "$location stereo is set for DVD input"
    }

    fun setRadio(): String {
        return "$location stereo is set for radio"
    }

    fun setVolume(volume: Int): String {
        return "$location stereo volume set to $volume"
    }
}