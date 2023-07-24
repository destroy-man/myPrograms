package ru.korobeynikov.p04annotationinject.other

import dagger.MapKey

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class EventHandlerKey(val value: EventHandlerType)