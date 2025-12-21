package ru.korobeynikov.ch06command.remotecontrol.garagedoor

import ru.korobeynikov.ch06command.command.Command

class GarageDoorUpCommand(private val garageDoor: GarageDoor) : Command {

    override fun execute(): String {
        return garageDoor.up()
    }

    override fun undo(): String {
        return garageDoor.down()
    }
}