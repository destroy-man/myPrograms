package ru.korobeynikov.p05objecttransmitmenttocomponent.di

import dagger.Component
import ru.korobeynikov.p05objecttransmitmenttocomponent.other.MainActivity

@Component(modules = [AppModule::class, StorageModule::class, NetworkModule::class, MainModule::class])
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}