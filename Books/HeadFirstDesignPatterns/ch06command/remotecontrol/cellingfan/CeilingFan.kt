package ru.korobeynikov.ch06command.remotecontrol.cellingfan

import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CeilingFanSpeed.HIGH
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CeilingFanSpeed.MEDIUM
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CeilingFanSpeed.LOW
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CeilingFanSpeed.OFF

class CeilingFan(private val location: String) {

    var speed = OFF

    fun high(): String {
        speed = HIGH
        return "$location ceiling fan is on high"
    }

    fun medium(): String {
        speed = MEDIUM
        return "$location ceiling fan is on medium"
    }

    fun low(): String {
        speed = LOW
        return "$location ceiling fan is on low"
    }

    fun off(): String {
        speed = OFF
        return "$location ceiling fan is off"
    }

    fun getCeilingFanSpeed(): CeilingFanSpeed {
        return speed
    }
}

enum class CeilingFanSpeed {
    HIGH, MEDIUM, LOW, OFF
}