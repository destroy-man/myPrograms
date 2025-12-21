package ru.korobeynikov.ch06command.remotecontrol.garagedoor

import ru.korobeynikov.ch06command.command.Command

class GarageDoorDownCommand(private val garageDoor: GarageDoor) : Command {

    override fun execute(): String {
        return garageDoor.down()
    }

    override fun undo(): String {
        return garageDoor.up()
    }
}