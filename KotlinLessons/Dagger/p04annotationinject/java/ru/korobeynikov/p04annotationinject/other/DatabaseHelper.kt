package ru.korobeynikov.p04annotationinject.other

import javax.inject.Inject

class DatabaseHelper @Inject constructor() {
    fun getEventHandlers() = setOf<EventHandler>()
}