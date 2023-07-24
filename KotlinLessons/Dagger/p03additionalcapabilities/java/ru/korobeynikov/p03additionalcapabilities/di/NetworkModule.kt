package ru.korobeynikov.p03additionalcapabilities.di

import dagger.Module
import dagger.Provides
import ru.korobeynikov.p03additionalcapabilities.other.ConnectionManager
import ru.korobeynikov.p03additionalcapabilities.other.NetworkUtils
import ru.korobeynikov.p03additionalcapabilities.other.ServerApi
import javax.inject.Named

@Module
class NetworkModule {

    @Provides
    fun provideNetworkUtils(connectionManager: ConnectionManager) = NetworkUtils(connectionManager)

    @Provides
    fun provideConnectionManager() = ConnectionManager()

    @Prod("1")
    @Provides
    fun provideServerApiProd1() = ServerApi("prod1.server.com")

    @Prod("2")
    @Provides
    fun provideServerApiProd2()=ServerApi("prod2.server.com")

    @Dev
    @Provides
    fun provideServerApiDev()=ServerApi("dev.server.com")
}