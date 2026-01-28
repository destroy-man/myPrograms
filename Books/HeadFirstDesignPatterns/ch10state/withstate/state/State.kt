package ru.korobeynikov.ch10state.withstate.state

interface State {

    fun insertQuarter(): String

    fun ejectQuarter(): String

    fun turnCrank(): String

    fun dispense(): String
}