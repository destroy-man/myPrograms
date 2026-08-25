package ru.korobeynikov.p06builderfactory.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p06builderfactory.network.NetworkUtils
import ru.korobeynikov.p06builderfactory.Utils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}