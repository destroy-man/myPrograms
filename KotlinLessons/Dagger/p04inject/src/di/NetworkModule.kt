package ru.korobeynikov.p04inject.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p04inject.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}