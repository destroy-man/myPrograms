package ru.korobeynikov.ch06command.remotecontrol.stereo

import ru.korobeynikov.ch06command.command.Command

class StereoOnCommand(private val stereo: Stereo) : Command {

    override fun execute(): String {
        return stereo.on()
    }

    override fun undo(): String {
        return stereo.off()
    }
}