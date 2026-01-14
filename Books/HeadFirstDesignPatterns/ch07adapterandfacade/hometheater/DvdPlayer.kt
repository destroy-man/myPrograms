package ru.korobeynikov.ch07adapterandfacade.hometheater

class DvdPlayer {

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

    fun pause(movie: String): String {
        return "${toString()} paused $movie"
    }

    fun play(movie: String): String {
        return "${toString()} playing \"$movie\""
    }

    fun setSurroundAudio(): String {
        return "${toString()} set to surround audio"
    }

    fun setTwoChannelAudio(): String {
        return "${toString()} set to two channel audio"
    }

    fun stop(movie: String): String {
        return "${toString()} stopped \"$movie\""
    }

    override fun toString(): String {
        return "Top-0-Line DVD player"
    }
}