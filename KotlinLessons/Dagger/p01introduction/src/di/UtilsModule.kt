package ru.korobeynikov.p01introduction.di

import dagger.Binds
import dagger.Module
import ru.korobeynikov.p01introduction.NetworkUtils
import ru.korobeynikov.p01introduction.Utils

@Module
interface UtilsModule {
    @Binds
    fun bindNetworkUtils(networkUtils: NetworkUtils): Utils
}