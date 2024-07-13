package ru.korobeynikov.chapter4.chapter4_1

interface Focusable {

    fun setFocus(b: Boolean) = "I ${if (b) "got" else "lost"} focus."

    fun showOff() = "I'm focusable!"
}