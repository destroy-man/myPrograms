package ru.korobeynikov.p11scope.di

import dagger.Module
import dagger.Provides
import dagger.multibindings.ElementsIntoSet
import dagger.multibindings.IntoMap
import ru.korobeynikov.p11scope.other.*

@Module
class EventModule {

    @IntoMap
    @EventHandlerKey(EventHandlerType.ANALYTICS)
    @Provides
    fun provideAnalytics(): EventHandler = Analytics()

    @IntoMap
    @EventHandlerKey(EventHandlerType.LOGGER)
    @Provides
    fun provideLogger(): EventHandler = Logger()

    @ElementsIntoSet
    @Provides
    fun provideDatabaseEventHandlers(databaseHelper: DatabaseHelper) = databaseHelper.getEventHandlers()
}