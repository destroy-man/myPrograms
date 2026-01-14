package ru.korobeynikov.ch07adapterandfacade.hometheater

class Amplifier {

    lateinit var tuner: Tuner
    lateinit var dvdPlayer: DvdPlayer
    lateinit var cdPlayer: CdPlayer

    fun on(): String {
        return "${toString()} on"
    }

    fun off(): String {
        return "${toString()} off"
    }

    fun setCd(cd: CdPlayer): String {
        cdPlayer = cd
        return "${toString()} setting CD player to $cdPlayer"
    }

    fun setDvd(dvd: DvdPlayer): String {
        dvdPlayer = dvd
        return "${toString()} setting DVD player to $dvdPlayer"
    }

    fun setStereoSound(): String {
        return "${toString()} stereo sound on"
    }

    fun setSurroundSound(): String {
        return "${toString()} surround sound on (5 speakers, 1 subwoofer)"
    }

    fun setTuner(tuner: Tuner): String {
        this.tuner = tuner
        return "${toString()} setting Tuner to ${this.tuner}"
    }

    fun setVolume(volume: Int): String {
        return "${toString()} setting volume to $volume"
    }

    override fun toString(): String {
        return "Top-0-Line Amplifier"
    }
}