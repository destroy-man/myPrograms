package ru.korobeynikov.p03additionalcapabilities.di

import dagger.Component
import ru.korobeynikov.p03additionalcapabilities.other.EventHandler
import ru.korobeynikov.p03additionalcapabilities.other.EventHandlerType

@Component(modules = [StorageModule::class, NetworkModule::class, MainModule::class, EventModule::class])
interface AppComponent {
    fun getEventHandlers(): Map<EventHandlerType, EventHandler>
}