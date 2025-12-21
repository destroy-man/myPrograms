package ru.korobeynikov.ch06command.simpleremotecontrol.garagedoor

import ru.korobeynikov.ch06command.command.SimpleCommand

class GarageDoorDownCommand(private val garageDoor: GarageDoor) : SimpleCommand {
    override fun execute(): String {
        return garageDoor.down()
    }
}