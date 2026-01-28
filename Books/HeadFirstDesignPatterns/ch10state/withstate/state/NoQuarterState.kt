package ru.korobeynikov.ch10state.withstate.state

import ru.korobeynikov.ch10state.withstate.GumballMachine

class NoQuarterState(private val gumballMachine: GumballMachine) : State {

    override fun insertQuarter(): String {
        gumballMachine.state = gumballMachine.hasQuarterState
        return "You inserted a quarter"
    }

    override fun ejectQuarter(): String {
        return "You haven't inserted a quarter"
    }

    override fun turnCrank(): String {
        return "You turned, but there's no quarter"
    }

    override fun dispense(): String {
        return "You need to pay first"
    }
}