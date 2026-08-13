package ru.korobeynikov.p01introduction.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p01introduction.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}