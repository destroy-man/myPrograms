package ru.korobeynikov.p07subcomponents.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p07subcomponents.other.ConnectionManager
import ru.korobeynikov.p07subcomponents.other.ServerApi

@Module
class NetworkModule {

    @Provides
    fun provideConnectionManager() = ConnectionManager()

    @Prod("1")
    @Provides
    fun provideServerApiProd1() = ServerApi("prod1.server.com")

    @Prod("2")
    @Provides
    fun provideServerApiProd2() = ServerApi("prod2.server.com")

    @Dev
    @Provides
    fun provideServerApiDev() = ServerApi("dev.server.com")
}