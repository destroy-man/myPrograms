package ru.korobeynikov.ch06command.command

interface Command {

    fun execute(): String

    fun undo(): String
}