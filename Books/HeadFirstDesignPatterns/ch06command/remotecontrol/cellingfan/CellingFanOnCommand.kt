package ru.korobeynikov.ch06command.remotecontrol.cellingfan

import ru.korobeynikov.ch06command.command.Command

class CellingFanOnCommand(private val ceilingFan: CeilingFan) : Command {

    override fun execute(): String {
        return ceilingFan.high()
    }

    override fun undo(): String {
        return ceilingFan.off()
    }
}