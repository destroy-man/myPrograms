package ru.korobeynikov.p11scope.other

import javax.inject.Inject

class DatabaseHelper @Inject constructor() {
    fun getEventHandlers() = setOf<EventHandler>()
}