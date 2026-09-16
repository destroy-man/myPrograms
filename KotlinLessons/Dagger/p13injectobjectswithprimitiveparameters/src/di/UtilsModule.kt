package ru.korobeynikov.p13injectobjectswithprimitiveparameters.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.Utils
import ru.korobeynikov.p13injectobjectswithprimitiveparameters.network.NetworkUtils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}