package ru.korobeynikov.ch07adapterandfacade.hometheater

class CdPlayer {

    lateinit var amplifier: Amplifier

    fun on(): String {
        return "${toString()} on"
    }

    fun off(): String {
        return "${toString()} off"
    }

    fun eject(): String {
        return "${toString()} eject"
    }

    fun pause(): String {
        return "${toString()} pause"
    }

    fun play(): String {
        return "${toString()} play"
    }

    fun stop(): String {
        return "${toString()} stop"
    }

    override fun toString(): String {
        return "Cd player"
    }
}