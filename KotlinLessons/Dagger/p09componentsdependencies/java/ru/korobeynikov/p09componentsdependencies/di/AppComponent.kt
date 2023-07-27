package ru.korobeynikov.p09componentsdependencies.di

import dagger.Component
import ru.korobeynikov.p09componentsdependencies.other.DatabaseHelper
import ru.korobeynikov.p09componentsdependencies.other.NetworkUtils

@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils
}