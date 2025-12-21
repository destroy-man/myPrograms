package ru.korobeynikov.ch06command.simpleremotecontrol

import ru.korobeynikov.ch06command.command.SimpleCommand

class SimpleRemoteControl {

    lateinit var slot: SimpleCommand

    fun setCommand(command: SimpleCommand) {
        slot = command
    }

    fun buttonWasPressed(): String {
        return slot.execute()
    }
}