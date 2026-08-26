package ru.korobeynikov.p07subcomponents.di

import dagger.Component
import ru.korobeynikov.p07subcomponents.database.DatabaseHelper
import ru.korobeynikov.p07subcomponents.network.NetworkUtils

@Component(modules = [StorageModule::class, NetworkModule::class, UtilsModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils

    fun getMainComponent(): MainComponent
}