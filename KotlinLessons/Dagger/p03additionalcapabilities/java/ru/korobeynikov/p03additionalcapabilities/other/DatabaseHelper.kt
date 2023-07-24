package ru.korobeynikov.p03additionalcapabilities.other

class DatabaseHelper(private val repository: Repository) {
    fun getEventHandlers() = setOf<EventHandler>()
}