package ru.korobeynikov.p09componentsdependencies.other

import javax.inject.Inject

class DatabaseHelper @Inject constructor() {
    fun getEventHandlers() = setOf<EventHandler>()
}