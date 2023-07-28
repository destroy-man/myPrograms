package ru.korobeynikov.p10componentslivetime.di

import dagger.Component
import ru.korobeynikov.p10componentslivetime.other.DatabaseHelper
import ru.korobeynikov.p10componentslivetime.other.NetworkUtils

@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils

    fun getOrderComponent(): OrderComponent
}