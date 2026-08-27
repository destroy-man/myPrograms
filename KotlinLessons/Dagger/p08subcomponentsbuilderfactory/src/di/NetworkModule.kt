package ru.korobeynikov.p08subcomponentsbuilderfactory.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p08subcomponentsbuilderfactory.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}