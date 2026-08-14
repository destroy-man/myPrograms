package ru.korobeynikov.p02creationofcomplexobjects.di

import dagger.Component
import ru.korobeynikov.p02creationofcomplexobjects.main.MainActivityRepository

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class, MainModule::class])
interface AppComponent {
    fun getMainActivityRepository(): MainActivityRepository
}