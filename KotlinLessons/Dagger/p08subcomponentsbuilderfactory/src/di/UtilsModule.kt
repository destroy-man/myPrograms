package ru.korobeynikov.p08subcomponentsbuilderfactory.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p08subcomponentsbuilderfactory.Utils
import ru.korobeynikov.p08subcomponentsbuilderfactory.network.NetworkUtils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}