package ru.korobeynikov.p05passingobjecttocomponent.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p05passingobjecttocomponent.network.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}