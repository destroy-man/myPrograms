package ru.korobeynikov.p03additionalfeatures.event.eventhandler

import dagger.MapKey

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class EventHandlerKey(val value: EventHandlerType)
