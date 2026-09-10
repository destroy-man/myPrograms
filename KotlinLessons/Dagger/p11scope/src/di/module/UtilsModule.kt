package ru.korobeynikov.p11scope.di.module

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p11scope.Utils
import ru.korobeynikov.p11scope.network.NetworkUtils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}