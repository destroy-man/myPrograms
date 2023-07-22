package ru.korobeynikov.p02dependenciesgraph.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p02dependenciesgraph.other.ConnectionManager
import ru.korobeynikov.p02dependenciesgraph.other.NetworkUtils

@Module
class NetworkModule {

    @Provides
    fun provideNetworkUtils(connectionManager: ConnectionManager) = NetworkUtils(connectionManager)

    @Provides
    fun provideConnectionManager() = ConnectionManager()
}