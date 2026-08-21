package ru.korobeynikov.p03additionalfeatures.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import ru.korobeynikov.p03additionalfeatures.event.Analytics
import ru.korobeynikov.p03additionalfeatures.event.eventhandler.EventHandler
import ru.korobeynikov.p03additionalfeatures.event.Logger
import ru.korobeynikov.p03additionalfeatures.event.eventhandler.EventHandlerKey
import ru.korobeynikov.p03additionalfeatures.event.eventhandler.EventHandlerType

@Module
class EventHandlerModule {

    /*IntoSet
    @IntoSet
    @Provides
    fun provideAnalytics(): EventHandler {
        return Analytics()
    }

    @IntoSet
    @Provides
    fun provideLogger(): EventHandler {
        return Logger()
    }
     */

    @IntoMap
    //@StringKey("analytics") Простой ключ
    @EventHandlerKey(EventHandlerType.ANALYTICS) //Ключ-перечисление
    @Provides
    fun provideAnalytics(): EventHandler {
        return Analytics()
    }

    @IntoMap
    //@StringKey("logger") Простой ключ
    @EventHandlerKey(EventHandlerType.LOGGER) //Ключ-перечисление
    @Provides
    fun provideLogger(): EventHandler {
        return Logger()
    }
}