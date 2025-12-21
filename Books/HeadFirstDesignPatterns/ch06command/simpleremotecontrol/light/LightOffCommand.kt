package ru.korobeynikov.ch06command.simpleremotecontrol.light

import ru.korobeynikov.ch06command.command.SimpleCommand

class LightOffCommand(private val light: Light) : SimpleCommand {
    override fun execute(): String {
        return light.off()
    }
}