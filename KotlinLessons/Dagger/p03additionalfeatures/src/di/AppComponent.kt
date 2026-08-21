package ru.korobeynikov.p03additionalfeatures.di

import dagger.Component
import ru.korobeynikov.p03additionalfeatures.event.eventhandler.EventHandler
import ru.korobeynikov.p03additionalfeatures.event.eventhandler.EventHandlerType

@Component(modules = [NetworkModule::class, UtilsModule::class, ServerApiModule::class, EventHandlerModule::class])
interface AppComponent {

    //Lazy
    //fun getNetworkUtils(): Lazy<NetworkUtils>

    //Provider

    //fun getNetworkUtils(): Provider<NetworkUtils>

    /*
    @Named("prod") Named для get метода
    @Prod("2") Qualifier
    fun getServerApiProd(): ServerApi

    fun injectMainActivity(mainActivity: MainActivity)
    */

    //IntoSet
    //fun getEventHandlers(): Set<EventHandler>

    //IntoMap с простым ключом
    //fun getEventHandlers(): Map<String, EventHandler>

    //IntoMap с ключом перечисление
    fun getEventHandlers(): Map<EventHandlerType, EventHandler>
}