package ru.korobeynikov.ch06command.remotecontrol.tv

import ru.korobeynikov.ch06command.command.Command

class TVOffCommand(private val tv: TV) : Command {

    val history = StringBuilder()

    override fun execute(): String {
        return tv.off()
    }

    override fun undo(): String {
        history.clear()
        history.appendLine(tv.on())
        history.append(tv.setInputChannel())
        return history.toString()
    }
}