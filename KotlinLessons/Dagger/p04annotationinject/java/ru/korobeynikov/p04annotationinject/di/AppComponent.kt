package ru.korobeynikov.p04annotationinject.di

import dagger.Component
import ru.korobeynikov.p04annotationinject.other.MainActivity

@Component(modules = [StorageModule::class, NetworkModule::class, MainModule::class, EventModule::class])
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}