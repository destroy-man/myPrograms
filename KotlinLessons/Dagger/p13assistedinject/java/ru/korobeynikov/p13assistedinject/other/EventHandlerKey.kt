package ru.korobeynikov.p13assistedinject.other

import dagger.MapKey

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class EventHandlerKey(val value: EventHandlerType)
