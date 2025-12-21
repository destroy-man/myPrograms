package ru.korobeynikov.ch06command.remotecontrol.light

import ru.korobeynikov.ch06command.command.Command

class LightOnCommand(private val light: Light) : Command {

    override fun execute(): String {
        return light.on()
    }

    override fun undo(): String {
        return light.off()
    }
}