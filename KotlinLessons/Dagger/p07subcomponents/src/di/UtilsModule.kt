package ru.korobeynikov.p07subcomponents.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p07subcomponents.Utils
import ru.korobeynikov.p07subcomponents.network.NetworkUtils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}