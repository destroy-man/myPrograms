package ru.korobeynikov.p02creationofcomplexobjects.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p02creationofcomplexobjects.network.ConnectionManager
import ru.korobeynikov.p02creationofcomplexobjects.network.NetworkUtils

@Module
class NetworkModule {

    @Provides
    fun provideConnectionManager(): ConnectionManager {
        return ConnectionManager()
    }

    @Provides
    fun provideNetworkUtils(connectionManager: ConnectionManager): NetworkUtils {
        return NetworkUtils(connectionManager)
    }
}