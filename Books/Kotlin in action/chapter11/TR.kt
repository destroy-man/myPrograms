package ru.korobeynikov.chapter11

class TR : Tag("tr") {
    fun td(init: TD.() -> Unit) = doInit(TD(), init)
}