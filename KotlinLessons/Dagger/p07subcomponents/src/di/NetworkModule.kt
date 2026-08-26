package ru.korobeynikov.p07subcomponents.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p07subcomponents.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}