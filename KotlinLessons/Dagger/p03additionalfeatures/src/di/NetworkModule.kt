package ru.korobeynikov.p03additionalfeatures.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p03additionalfeatures.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}