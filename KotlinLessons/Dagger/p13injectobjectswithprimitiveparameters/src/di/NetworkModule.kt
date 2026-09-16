package ru.korobeynikov.p13injectobjectswithprimitiveparameters.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}