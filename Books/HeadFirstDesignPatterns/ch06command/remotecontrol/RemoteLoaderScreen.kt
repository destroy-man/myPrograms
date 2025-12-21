package ru.korobeynikov.ch06command.remotecontrol

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import ru.korobeynikov.ch06command.command.MacroCommand
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CeilingFan
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CeilingFanHighCommand
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CeilingFanMediumCommand
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CellingFanOffCommand
import ru.korobeynikov.ch06command.remotecontrol.cellingfan.CellingFanOnCommand
import ru.korobeynikov.ch06command.remotecontrol.garagedoor.GarageDoor
import ru.korobeynikov.ch06command.remotecontrol.hottub.Hottub
import ru.korobeynikov.ch06command.remotecontrol.hottub.HottubOffCommand
import ru.korobeynikov.ch06command.remotecontrol.hottub.HottubOnCommand
import ru.korobeynikov.ch06command.remotecontrol.light.Light
import ru.korobeynikov.ch06command.remotecontrol.light.LightOffCommand
import ru.korobeynikov.ch06command.remotecontrol.light.LightOnCommand
import ru.korobeynikov.ch06command.remotecontrol.stereo.Stereo
import ru.korobeynikov.ch06command.remotecontrol.stereo.StereoOffCommand
import ru.korobeynikov.ch06command.remotecontrol.stereo.StereoOnCommand
import ru.korobeynikov.ch06command.remotecontrol.stereo.StereoOnWithCDCommand
import ru.korobeynikov.ch06command.remotecontrol.tv.TV
import ru.korobeynikov.ch06command.remotecontrol.tv.TVOffCommand
import ru.korobeynikov.ch06command.remotecontrol.tv.TVOnCommand

//Паттерн Команда
@Composable
fun RemoteLoaderScreen() {
    //С использованием лямбд
    val remoteControl = RemoteControlLambda()

    val livingRoomLight = Light("Living Room")
    val kitchenLight = Light("Kitchen")
    val ceilingFan = CeilingFan("Living Room")
    val garageDoor = GarageDoor("Main house")
    val stereo = Stereo("Living Room")

    val stereoOnWithCD = {
        val history = StringBuilder()
        history.appendLine(stereo.on())
        history.appendLine(stereo.setCD())
        history.append(stereo.setVolume(11))
        history.toString()
    }

    remoteControl.setCommand(0, livingRoomLight::on, livingRoomLight::off)
    remoteControl.setCommand(1, kitchenLight::on, kitchenLight::off)
    remoteControl.setCommand(2, ceilingFan::high, ceilingFan::off)
    remoteControl.setCommand(3, stereoOnWithCD, stereo::off)
    remoteControl.setCommand(4, garageDoor::up, garageDoor::down)

    Text(remoteControl.toString())

    Text(remoteControl.onButtonWasPushed(0))
    Text(remoteControl.offButtonWasPushed(0))
    Text(remoteControl.onButtonWasPushed(1))
    Text(remoteControl.offButtonWasPushed(1))
    Text(remoteControl.onButtonWasPushed(2))
    Text(remoteControl.offButtonWasPushed(2))
    Text(remoteControl.onButtonWasPushed(3))
    Text(remoteControl.offButtonWasPushed(3))
}

@Composable
fun RemoteLoaderScreenMacroCommand() {
    val remoteControl = RemoteControlWithUndo()

    val light = Light("Living Room")
    val tv = TV("Living Room")
    val stereo = Stereo("Living Room")
    val hottub = Hottub()

    val lightOn = LightOnCommand(light)
    val tvOn = TVOnCommand(tv)
    val stereoOn = StereoOnCommand(stereo)
    val hottubOn = HottubOnCommand(hottub)

    val lightOff = LightOffCommand(light)
    val tvOff = TVOffCommand(tv)
    val stereoOff = StereoOffCommand(stereo)
    val hottubOff = HottubOffCommand(hottub)

    val partyOn = arrayOf(lightOn, stereoOn, tvOn, hottubOn)
    val partyOff = arrayOf(lightOff, stereoOff, tvOff, hottubOff)

    val partyOnMacro = MacroCommand(partyOn)
    val partyOffMacro = MacroCommand(partyOff)

    remoteControl.setCommand(0, partyOnMacro, partyOffMacro)

    Text(remoteControl.toString())
    Text("--- Pushing Macro On ---")
    Text(remoteControl.onButtonWasPushed(0))
    Text("--- Pushing Macro Off ---")
    Text(remoteControl.offButtonWasPushed(0))
}

@Composable
fun RemoteLoaderScreenCeilingFanSpeed() {
    val remoteControl = RemoteControlWithUndo()

    val ceilingFan = CeilingFan("Living Room")

    val ceilingFanMedium = CeilingFanMediumCommand(ceilingFan)
    val ceilingFanHigh = CeilingFanHighCommand(ceilingFan)
    val ceilingFanOff = CellingFanOffCommand(ceilingFan)

    remoteControl.setCommand(0, ceilingFanMedium, ceilingFanOff)
    remoteControl.setCommand(1, ceilingFanHigh, ceilingFanOff)

    Text(remoteControl.onButtonWasPushed(0))
    Text(remoteControl.offButtonWasPushed(0))
    Text(remoteControl.toString())
    Text("${remoteControl.undoButtonWasPushed()}\n")

    Text(remoteControl.onButtonWasPushed(1))
    Text(remoteControl.toString())
    Text(remoteControl.undoButtonWasPushed())
}

@Composable
fun RemoteLoaderScreenWithUndo() {
    val remoteControl = RemoteControlWithUndo()
    val livingRoomLight = Light("Living Room")

    val livingRoomLightOn = LightOnCommand(livingRoomLight)
    val livingRoomLightOff = LightOffCommand(livingRoomLight)

    remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff)

    Text(remoteControl.onButtonWasPushed(0))
    Text(remoteControl.offButtonWasPushed(0))
    Text(remoteControl.toString())
    Text("${remoteControl.undoButtonWasPushed()}\n")
    Text(remoteControl.offButtonWasPushed(0))
    Text(remoteControl.onButtonWasPushed(0))
    Text(remoteControl.toString())
    Text(remoteControl.undoButtonWasPushed())
}

@Composable
fun RemoteLoaderScreenWithoutUndo() {
    val remoteControl = RemoteControl()
    val livingRoomLight = Light("Living Room")
    val kitchenLight = Light("Kitchen")
    val ceilingFan = CeilingFan("Living Room")
    val stereo = Stereo("Living Room")

    val livingRoomLightOn = LightOnCommand(livingRoomLight)
    val livingRoomLightOff = LightOffCommand(livingRoomLight)
    val kitchenLightOn = LightOnCommand(kitchenLight)
    val kitchenLightOff = LightOffCommand(kitchenLight)

    val ceilingFanOn = CellingFanOnCommand(ceilingFan)
    val cellingFanOff = CellingFanOffCommand(ceilingFan)

    val stereoOnWithCD = StereoOnWithCDCommand(stereo)
    val stereoOff = StereoOffCommand(stereo)

    remoteControl.setCommand(0, livingRoomLightOn, livingRoomLightOff)
    remoteControl.setCommand(1, kitchenLightOn, kitchenLightOff)
    remoteControl.setCommand(2, ceilingFanOn, cellingFanOff)
    remoteControl.setCommand(3, stereoOnWithCD, stereoOff)

    Text(remoteControl.toString())

    Text(remoteControl.onButtonWasPushed(0))
    Text(remoteControl.offButtonWasPushed(0))
    Text(remoteControl.onButtonWasPushed(1))
    Text(remoteControl.offButtonWasPushed(1))
    Text(remoteControl.onButtonWasPushed(2))
    Text(remoteControl.offButtonWasPushed(2))
    Text(remoteControl.onButtonWasPushed(3))
    Text(remoteControl.offButtonWasPushed(3))
}