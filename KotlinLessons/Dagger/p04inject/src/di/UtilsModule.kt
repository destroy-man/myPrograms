package ru.korobeynikov.p04inject.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p04inject.network.NetworkUtils
import ru.korobeynikov.p04inject.Utils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}