package ru.korobeynikov.ch10state.withstate.state

import ru.korobeynikov.ch10state.withstate.GumballMachine

class SoldOutState(private val gumballMachine: GumballMachine) : State {

    override fun insertQuarter(): String {
        return "You can't insert a quarter, the machine is sold out"
    }

    override fun ejectQuarter(): String {
        return "You can't eject, you haven't inserted a quarter yet"
    }

    override fun turnCrank(): String {
        return "You turned, but there are no gumballs"
    }

    override fun dispense(): String {
        return "No gumball dispensed"
    }

    override fun toString(): String {
        return "sold out"
    }
}