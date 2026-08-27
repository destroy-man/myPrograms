package ru.korobeynikov.p09componentsdependencies.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p09componentsdependencies.Utils
import ru.korobeynikov.p09componentsdependencies.network.NetworkUtils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}