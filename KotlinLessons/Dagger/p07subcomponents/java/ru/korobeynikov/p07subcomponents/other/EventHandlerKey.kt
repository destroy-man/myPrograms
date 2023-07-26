package ru.korobeynikov.p07subcomponents.other

import dagger.MapKey

@MapKey
@Retention(AnnotationRetention.RUNTIME)
annotation class EventHandlerKey(val value: EventHandlerType)