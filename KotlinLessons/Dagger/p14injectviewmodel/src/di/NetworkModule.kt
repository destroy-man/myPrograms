package ru.korobeynikov.p14injectviewmodel.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p14injectviewmodel.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}