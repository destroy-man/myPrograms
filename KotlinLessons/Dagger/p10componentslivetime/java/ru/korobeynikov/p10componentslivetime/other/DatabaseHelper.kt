package ru.korobeynikov.p10componentslivetime.other

import javax.inject.Inject

class DatabaseHelper @Inject constructor() {
    fun getEventHandlers() = setOf<EventHandler>()
}