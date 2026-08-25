package ru.korobeynikov.p06builderfactory.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p06builderfactory.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}