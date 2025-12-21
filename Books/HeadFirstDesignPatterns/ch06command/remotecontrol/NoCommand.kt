package ru.korobeynikov.ch06command.remotecontrol

import ru.korobeynikov.ch06command.command.Command

class NoCommand : Command {

    override fun execute(): String {
        return ""
    }

    override fun undo(): String {
        return ""
    }
}