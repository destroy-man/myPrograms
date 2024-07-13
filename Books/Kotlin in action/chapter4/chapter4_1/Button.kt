package ru.korobeynikov.chapter4.chapter4_1

class Button : Clickable, Focusable, View {

    override fun click() = "I was clicked."

    override fun showOff(): String {
        return " ${super<Clickable>.showOff()}\n ${super<Focusable>.showOff()}"
    }

    override fun getCurrentState(): State = ButtonState()

    override fun restoreState(state: State) {}

    class ButtonState : State
}