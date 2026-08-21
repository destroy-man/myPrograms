package ru.korobeynikov.p03additionalfeatures.event.eventhandler

import ru.korobeynikov.p03additionalfeatures.event.Event

interface EventHandler {
    fun handle(event: Event)
}