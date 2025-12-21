package ru.korobeynikov.ch06command.remotecontrol.cellingfan

import ru.korobeynikov.ch06command.command.Command

class CellingFanOffCommand(private val ceilingFan: CeilingFan) : Command {

    var prevSpeed = CeilingFanSpeed.OFF

    override fun execute(): String {
        prevSpeed = ceilingFan.getCeilingFanSpeed()
        return ceilingFan.off()
    }

    override fun undo(): String {
        return when (prevSpeed) {
            CeilingFanSpeed.HIGH -> ceilingFan.high()
            CeilingFanSpeed.MEDIUM -> ceilingFan.medium()
            CeilingFanSpeed.LOW -> ceilingFan.low()
            CeilingFanSpeed.OFF -> ceilingFan.off()
        }
    }
}