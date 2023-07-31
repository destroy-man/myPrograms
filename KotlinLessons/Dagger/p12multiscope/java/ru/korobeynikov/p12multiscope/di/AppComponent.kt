package ru.korobeynikov.p12multiscope.di

import dagger.Component
import ru.korobeynikov.p12multiscope.other.DatabaseHelper
import ru.korobeynikov.p12multiscope.other.NetworkUtils

@AppScope
@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils

    fun getOrderComponentFactory(): OrderComponent.Factory

    fun getUserComponentFactory(): UserComponent.Factory
}