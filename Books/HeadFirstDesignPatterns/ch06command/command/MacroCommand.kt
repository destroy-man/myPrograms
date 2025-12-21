package ru.korobeynikov.ch06command.command

class MacroCommand(private val commands: Array<Command>) : Command {

    val history = StringBuilder()

    override fun execute(): String {
        history.clear()
        commands.forEach { command ->
            history.appendLine(command.execute())
        }
        return history.toString()
    }

    override fun undo(): String {
        history.clear()
        commands.reversedArray().forEach { command ->
            history.appendLine(command.undo())
        }
        return history.toString()
    }
}