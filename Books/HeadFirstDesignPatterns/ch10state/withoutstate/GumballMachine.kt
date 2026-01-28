package ru.korobeynikov.ch10state.withoutstate

import ru.korobeynikov.ch10state.withoutstate.GumballMachine.GumballStates.SOLD_OUT
import ru.korobeynikov.ch10state.withoutstate.GumballMachine.GumballStates.NO_QUARTER
import ru.korobeynikov.ch10state.withoutstate.GumballMachine.GumballStates.HAS_QUARTER
import ru.korobeynikov.ch10state.withoutstate.GumballMachine.GumballStates.SOLD

class GumballMachine(count: Int) {

    var state = SOLD_OUT
    var countBalls = 0

    init {
        countBalls = count
        if (countBalls > 0) {
            state = NO_QUARTER
        }
    }

    fun insertQuarter(): String {
        return when (state) {
            HAS_QUARTER -> {
                "You can't insert another quarter"
            }

            NO_QUARTER -> {
                state = HAS_QUARTER
                "You inserted a quarter"
            }

            SOLD_OUT -> {
                "You can't insert a quarter, the machine is sold out"
            }

            SOLD -> {
                "Please wait, we're already giving you gumball"
            }
        }
    }

    fun ejectQuarter(): String {
        return when (state) {
            HAS_QUARTER -> {
                state = NO_QUARTER
                "Quarter returned"
            }

            NO_QUARTER -> {
                "You haven't inserted a quarter"
            }

            SOLD -> {
                "Sorry, you already turned the crank"
            }

            SOLD_OUT -> {
                "You can't eject, you haven't inserted a quarter yet"
            }
        }
    }

    fun turnCrank(): String {
        return when (state) {
            SOLD -> {
                "Turning twice doesn't get you gumball!"
            }

            NO_QUARTER -> {
                "You turned but there's no quarter"
            }

            SOLD_OUT -> {
                "You turned, but there are no gumballs"
            }

            HAS_QUARTER -> {
                state = SOLD
                "You turned...\n${dispense()}"
            }
        }
    }

    fun dispense(): String {
        return when (state) {
            SOLD -> {
                val history = StringBuilder()
                history.append("A gumball comes rolling out the slot")
                countBalls--
                if (countBalls == 0) {
                    history.append("\nOops, out of gumballs!")
                    state = SOLD_OUT
                } else {
                    state = NO_QUARTER
                }
                history.toString()
            }

            NO_QUARTER -> {
                "You need to pay first"
            }

            SOLD_OUT -> {
                "No gumball dispensed"
            }

            HAS_QUARTER -> {
                "No gumball dispensed"
            }
        }
    }

    override fun toString(): String {
        val result = StringBuilder("\nMighty Gumball, Inc.\n")
        result.appendLine("Java-enabled Standing Gumball Model #2004")
        result.appendLine("Inventory: $countBalls gumballs")
        when (state) {
            SOLD_OUT -> {
                result.appendLine("Machine is sold out")
            }

            NO_QUARTER -> {
                result.appendLine("Machine is waiting for quarter")
            }

            HAS_QUARTER -> {
                result.appendLine("Machine has a quarter")
            }

            SOLD -> {
                result.appendLine("Machine is sold")
            }
        }
        return result.toString()
    }

    enum class GumballStates {
        SOLD_OUT, NO_QUARTER, HAS_QUARTER, SOLD
    }
}