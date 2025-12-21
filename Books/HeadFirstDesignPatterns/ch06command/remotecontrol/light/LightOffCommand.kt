package ru.korobeynikov.ch06command.remotecontrol.light

import ru.korobeynikov.ch06command.command.Command

class LightOffCommand(private val light: Light) : Command {

    override fun execute(): String {
        return light.off()
    }

    override fun undo(): String {
        return light.on()
    }
}