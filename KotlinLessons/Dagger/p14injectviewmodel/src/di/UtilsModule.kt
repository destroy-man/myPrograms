package ru.korobeynikov.p14injectviewmodel.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p14injectviewmodel.Utils
import ru.korobeynikov.p14injectviewmodel.network.NetworkUtils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}