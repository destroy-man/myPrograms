package ru.korobeynikov.ch10state.withstate

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GumballMachineScreen() {
    //Паттерн Состояние
    val gumballMachine = GumballMachine(5)
    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Text(gumballMachine.toString())

        Text(gumballMachine.insertQuarter())
        Text(gumballMachine.turnCrank())

        Text(gumballMachine.toString())

        Text(gumballMachine.insertQuarter())
        Text(gumballMachine.turnCrank())
        Text(gumballMachine.insertQuarter())
        Text(gumballMachine.turnCrank())

        Text(gumballMachine.toString())
    }
}