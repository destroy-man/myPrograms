package ru.korobeynikov.ch06command.remotecontrol.stereo

import ru.korobeynikov.ch06command.command.Command

class StereoOffCommand(private val stereo: Stereo) : Command {

    private val history = StringBuilder()

    override fun execute(): String {
        return stereo.off()
    }

    override fun undo(): String {
        history.clear()
        history.appendLine(stereo.on())
        history.appendLine(stereo.setCD())
        history.append(stereo.setVolume(11))
        return history.toString()
    }
}