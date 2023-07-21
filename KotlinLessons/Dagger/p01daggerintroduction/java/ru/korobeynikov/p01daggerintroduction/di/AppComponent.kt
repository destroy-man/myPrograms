package ru.korobeynikov.p01daggerintroduction.di

import dagger.Component
import ru.korobeynikov.p01daggerintroduction.other.MainActivity

@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {
    fun injectMainActivity(mainActivity: MainActivity)
}