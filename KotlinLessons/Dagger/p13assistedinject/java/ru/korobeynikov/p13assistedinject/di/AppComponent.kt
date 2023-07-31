package ru.korobeynikov.p13assistedinject.di

import dagger.Component
import ru.korobeynikov.p13assistedinject.other.DatabaseHelper
import ru.korobeynikov.p13assistedinject.other.NetworkUtils

@AppScope
@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils

    fun getOrderComponentFactory(): OrderComponent.Factory

    fun getUserComponentFactory(): UserComponent.Factory
}