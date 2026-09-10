package ru.korobeynikov.p11scope.di.module

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p11scope.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}