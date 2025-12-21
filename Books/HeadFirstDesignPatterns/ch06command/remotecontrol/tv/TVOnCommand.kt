package ru.korobeynikov.ch06command.remotecontrol.tv

import ru.korobeynikov.ch06command.command.Command

class TVOnCommand(private val tv: TV) : Command {

    val history = StringBuilder()

    override fun execute(): String {
        history.clear()
        history.appendLine(tv.on())
        history.append(tv.setInputChannel())
        return history.toString()
    }

    override fun undo(): String {
        return tv.off()
    }
}