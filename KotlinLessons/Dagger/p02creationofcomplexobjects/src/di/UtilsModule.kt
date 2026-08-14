package ru.korobeynikov.p02creationofcomplexobjects.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p02creationofcomplexobjects.network.NetworkUtils
import ru.korobeynikov.p02creationofcomplexobjects.Utils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}