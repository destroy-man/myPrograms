package ru.korobeynikov.p13assistedinject.other

import javax.inject.Inject

class DatabaseHelper @Inject constructor() {
    fun getEventHandlers() = setOf<EventHandler>()
}