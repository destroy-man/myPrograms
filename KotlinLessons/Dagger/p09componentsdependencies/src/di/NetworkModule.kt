package ru.korobeynikov.p09componentsdependencies.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p09componentsdependencies.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}