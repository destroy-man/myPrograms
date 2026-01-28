package ru.korobeynikov.ch10state.withstate.state

import ru.korobeynikov.ch10state.withstate.GumballMachine

class SoldState(private val gumballMachine: GumballMachine) : State {

    override fun insertQuarter(): String {
        return "Please wait, we're already giving you a gumball"
    }

    override fun ejectQuarter(): String {
        return "Sorry, you already turned the crank"
    }

    override fun turnCrank(): String {
        return "Turning twice doesn't get you another gumball!"
    }

    override fun dispense(): String {
        val history = StringBuilder(gumballMachine.releaseBall())
        if (gumballMachine.count > 0) {
            gumballMachine.state = gumballMachine.noQuarterState
        } else {
            gumballMachine.state = gumballMachine.soldOutState
            history.append("Oops, out of gumballs!")
        }
        return history.toString()
    }
}