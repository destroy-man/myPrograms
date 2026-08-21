package ru.korobeynikov.p03additionalfeatures.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p03additionalfeatures.network.NetworkUtils
import ru.korobeynikov.p03additionalfeatures.Utils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}