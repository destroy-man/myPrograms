package ru.korobeynikov.ch10state.withstate.state

import ru.korobeynikov.ch10state.withstate.GumballMachine
import kotlin.random.Random

class HasQuarterState(private val gumballMachine: GumballMachine) : State {

    override fun insertQuarter(): String {
        return "You can't insert another quarter"
    }

    override fun ejectQuarter(): String {
        gumballMachine.state = gumballMachine.noQuarterState
        return "Quarter returned"
    }

    override fun turnCrank(): String {
        val winner = Random.nextInt(10)
        if (winner == 0 && gumballMachine.count > 1) {
            gumballMachine.state = gumballMachine.winnerState
        } else {
            gumballMachine.state = gumballMachine.soldState
        }
        return "You turned...\n"
    }

    override fun dispense(): String {
        return "No gumball dispensed"
    }
}