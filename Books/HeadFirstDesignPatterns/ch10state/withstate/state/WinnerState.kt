package ru.korobeynikov.ch10state.withstate.state

import ru.korobeynikov.ch10state.withstate.GumballMachine

class WinnerState(private val gumballMachine: GumballMachine) : State {

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
        if (gumballMachine.count == 0) {
            gumballMachine.state = gumballMachine.soldOutState
        } else {
            history.appendLine("\n${gumballMachine.releaseBall()}")
            history.append("YOU'RE A WINNER! You got two gumballs for you quarter")
            if (gumballMachine.count > 0) {
                gumballMachine.state = gumballMachine.noQuarterState
            } else {
                history.append("Oops, out of gumballs!")
                gumballMachine.state = gumballMachine.soldOutState
            }
        }
        return history.toString()
    }
}