package ru.korobeynikov.ch06command.remotecontrol.hottub

import ru.korobeynikov.ch06command.command.Command

class HottubOnCommand(private val hottub: Hottub) : Command {

    private val history = StringBuilder()

    override fun execute(): String {
        hottub.setTemperature(104)
        history.clear()
        history.appendLine(hottub.on())
        history.append(hottub.circulate())
        return history.toString()
    }

    override fun undo(): String {
        hottub.setTemperature(98)
        return hottub.off()
    }
}