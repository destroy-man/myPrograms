package ru.korobeynikov.chapter4.chapter4_1

abstract class Animated {

    abstract fun animate()

    open fun stopAnimating() {}

    fun animateTwice() {}
}