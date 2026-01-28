package ru.korobeynikov.ch10state.withstate

import ru.korobeynikov.ch10state.withstate.state.HasQuarterState
import ru.korobeynikov.ch10state.withstate.state.NoQuarterState
import ru.korobeynikov.ch10state.withstate.state.SoldOutState
import ru.korobeynikov.ch10state.withstate.state.SoldState
import ru.korobeynikov.ch10state.withstate.state.WinnerState

class GumballMachine(numberGumballs: Int) {

    val soldOutState = SoldOutState(this)
    val noQuarterState = NoQuarterState(this)
    val hasQuarterState = HasQuarterState(this)
    val soldState = SoldState(this)
    val winnerState = WinnerState(this)

    var count = numberGumballs
    var state = if (numberGumballs > 0) {
        noQuarterState
    } else {
        soldOutState
    }

    fun insertQuarter(): String {
        return state.insertQuarter()
    }

    fun ejectQuarter(): String {
        return state.ejectQuarter()
    }

    fun turnCrank(): String {
        return state.turnCrank() + state.dispense()
    }

    fun releaseBall(): String {
        if (count != 0) {
            count--
        }
        return "A gumball comes rolling out the slot..."
    }

    override fun toString(): String {
        val result = StringBuilder("\nMighty Gumball, Inc.\n")
        result.appendLine("Java-enabled Standing Gumball Model #2004")
        result.appendLine("Inventory: $count gumballs")
        when (state) {
            soldOutState -> {
                result.appendLine("Machine is sold out")
            }

            noQuarterState -> {
                result.appendLine("Machine is waiting for quarter")
            }

            hasQuarterState -> {
                result.appendLine("Machine has a quarter")
            }

            soldState -> {
                result.appendLine("Machine is sold")
            }
        }
        return result.toString()
    }
}