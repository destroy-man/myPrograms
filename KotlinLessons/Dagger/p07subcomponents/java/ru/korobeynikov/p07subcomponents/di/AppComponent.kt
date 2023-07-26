package ru.korobeynikov.p07subcomponents.di

import dagger.Component
import ru.korobeynikov.p07subcomponents.other.DatabaseHelper
import ru.korobeynikov.p07subcomponents.other.NetworkUtils

@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils

    fun getMainComponent(): MainComponent
}