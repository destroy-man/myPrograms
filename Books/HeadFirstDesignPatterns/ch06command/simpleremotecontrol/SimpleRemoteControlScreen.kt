package ru.korobeynikov.ch06command.simpleremotecontrol

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch06command.simpleremotecontrol.garagedoor.GarageDoor
import ru.korobeynikov.ch06command.simpleremotecontrol.garagedoor.GarageDoorOpenCommand
import ru.korobeynikov.ch06command.simpleremotecontrol.light.Light
import ru.korobeynikov.ch06command.simpleremotecontrol.light.LightOnCommand

@Composable
fun SimpleRemoteControlScreen() {
    //Паттерн Команда - упрощенная реализация
    val remote = SimpleRemoteControl()
    val light = Light()
    val garageDoor = GarageDoor()
    val lightOn = LightOnCommand(light)
    val garageOpen = GarageDoorOpenCommand(garageDoor)
    remote.setCommand(lightOn)
    Text(remote.buttonWasPressed())
    remote.setCommand(garageOpen)
    Text(remote.buttonWasPressed())
}