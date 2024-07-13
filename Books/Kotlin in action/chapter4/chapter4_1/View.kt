package ru.korobeynikov.chapter4.chapter4_1

interface View {

    fun getCurrentState(): State

    fun restoreState(state: State) {}
}