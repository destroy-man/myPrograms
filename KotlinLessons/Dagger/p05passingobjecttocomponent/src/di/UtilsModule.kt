package ru.korobeynikov.p05passingobjecttocomponent.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p05passingobjecttocomponent.network.NetworkUtils
import ru.korobeynikov.p05passingobjecttocomponent.Utils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}