package ru.korobeynikov.ch07adapterandfacade.hometheater

class PopcornPopper {

    fun on(): String {
        return "${toString()} on"
    }

    fun off(): String {
        return "${toString()} off"
    }

    fun pop(): String {
        return "${toString()} popping popcorn!"
    }

    override fun toString(): String {
        return "Popcorn Popper"
    }
}