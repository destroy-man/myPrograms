package ru.korobeynikov.p09componentsdependencies.di

import dagger.Component
import ru.korobeynikov.p09componentsdependencies.Utils
import ru.korobeynikov.p09componentsdependencies.database.DatabaseHelper

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getUtils(): Utils
}