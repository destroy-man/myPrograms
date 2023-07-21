package ru.korobeynikov.p01daggerintroduction.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p01daggerintroduction.other.NetworkUtils

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils() = NetworkUtils()
}