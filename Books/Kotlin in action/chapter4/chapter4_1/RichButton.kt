package ru.korobeynikov.chapter4.chapter4_1

open class RichButton : Clickable {

    fun disable() {}

    open fun animate() {}

    final override fun click() = ""
}