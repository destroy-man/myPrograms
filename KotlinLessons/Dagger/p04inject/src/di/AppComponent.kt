package ru.korobeynikov.p04inject.di

import dagger.Component
import ru.korobeynikov.p04inject.main.MainActivity

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class, ServerApiModule::class])
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}