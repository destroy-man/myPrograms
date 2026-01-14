package ru.korobeynikov.ch07adapterandfacade.hometheater

class Projector() {

    lateinit var dvdPlayer: DvdPlayer

    fun on(): String {
        return "${toString()} on"
    }

    fun off(): String {
        return "${toString()} off"
    }

    fun tvMode(): String {
        return "${toString()} in tv mode"
    }

    fun wideScreenMode(): String {
        return "${toString()} in widescreen mode (16x9 aspect ratio)"
    }

    override fun toString(): String {
        return "Top-0-Line Projector"
    }
}