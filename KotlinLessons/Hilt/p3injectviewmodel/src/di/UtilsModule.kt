package ru.korobeynikov.p3injectviewmodel.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.korobeynikov.p3injectviewmodel.Utils
import ru.korobeynikov.p3injectviewmodel.network.NetworkUtils

@Module
@InstallIn(ViewModelComponent::class)
interface UtilsModule {
    @Binds
    fun bindUtils(networkUtils: NetworkUtils): Utils
}