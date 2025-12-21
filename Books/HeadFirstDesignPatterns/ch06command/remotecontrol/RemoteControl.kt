package ru.korobeynikov.ch06command.remotecontrol

import ru.korobeynikov.ch06command.command.Command

class RemoteControl {

    val noCommand: Command = NoCommand()
    val onCommands = Array(7) { noCommand }
    val offCommands = Array(7) { noCommand }

    fun setCommand(slot: Int, onCommand: Command, offCommand: Command) {
        onCommands[slot] = onCommand
        offCommands[slot] = offCommand
    }

    fun onButtonWasPushed(slot: Int): String {
        return onCommands[slot].execute()
    }

    fun offButtonWasPushed(slot: Int): String {
        return offCommands[slot].execute()
    }

    override fun toString(): String {
        val stringBuff = StringBuffer("\n------ Remote Control ------\n")
        for (i in 0 until onCommands.size) {
            stringBuff.append(
                "[slot $i] ${onCommands[i].javaClass.simpleName}   " +
                        "${offCommands[i].javaClass.simpleName}\n"
            )
        }
        return stringBuff.toString()
    }
}