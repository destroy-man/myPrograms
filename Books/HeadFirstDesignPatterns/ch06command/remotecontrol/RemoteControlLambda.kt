package ru.korobeynikov.ch06command.remotecontrol

class RemoteControlLambda {

    val noCommand = { "" }
    val onCommands = Array(7) { noCommand }
    val offCommands = Array(7) { noCommand }
    var undoCommand = noCommand

    fun setCommand(slot: Int, onCommand: () -> String, offCommand: () -> String) {
        onCommands[slot] = onCommand
        offCommands[slot] = offCommand
    }

    fun onButtonWasPushed(slot: Int): String {
        undoCommand = onCommands[slot]
        return onCommands[slot].invoke()
    }

    fun offButtonWasPushed(slot: Int): String {
        undoCommand = offCommands[slot]
        return offCommands[slot].invoke()
    }

    fun undoButtonWasPushed(): String {
        return undoCommand.invoke()
    }

    override fun toString(): String {
        val stringBuff = StringBuffer("\n------ Remote Control ------\n")
        for (i in 0 until onCommands.size) {
            stringBuff.append(
                "[slot $i] ${onCommands[i].javaClass.simpleName}   " +
                        "${offCommands[i].javaClass.simpleName}\n"
            )
        }
        stringBuff.append("[undo] ${undoCommand.javaClass.simpleName}\n")
        return stringBuff.toString()
    }
}