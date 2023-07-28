package ru.korobeynikov.p11scope.di

import dagger.Component
import ru.korobeynikov.p11scope.other.DatabaseHelper
import ru.korobeynikov.p11scope.other.NetworkUtils

@AppScope
@Component(modules = [StorageModule::class, NetworkModule::class])
interface AppComponent {

    fun getDatabaseHelper(): DatabaseHelper

    fun getNetworkUtils(): NetworkUtils

    fun getOrderComponent(): OrderComponent
}