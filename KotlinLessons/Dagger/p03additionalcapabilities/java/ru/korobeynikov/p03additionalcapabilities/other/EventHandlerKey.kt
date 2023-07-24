package ru.korobeynikov.p03additionalcapabilities.other

import dagger.MapKey

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class EventHandlerKey(val value: EventHandlerType)