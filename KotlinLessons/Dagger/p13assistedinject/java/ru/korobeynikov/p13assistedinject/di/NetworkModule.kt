package ru.korobeynikov.p13assistedinject.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p13assistedinject.other.ConnectionManager
import ru.korobeynikov.p13assistedinject.other.NetworkUtils
import ru.korobeynikov.p13assistedinject.other.ServerApi

@Module
class NetworkModule {

    @Provides
    fun provideConnectionManager() = ConnectionManager()

    @Prod("1")
    @Provides
    fun provideServerApiProd1() = ServerApi(NetworkUtils(), "prod1.server.com", "50")

    @Prod("2")
    @Provides
    fun provideServerApiProd2() = ServerApi(NetworkUtils(), "prod2.server.com", "50")

    @Dev
    @Provides
    fun provideServerApiDev() = ServerApi(NetworkUtils(), "dev.server.com", "50")
}