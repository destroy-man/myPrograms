package ru.korobeynikov.p2modulesscope.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import ru.korobeynikov.p2modulesscope.network.NetworkUtils
import ru.korobeynikov.p2modulesscope.Utils

@Module
@InstallIn(ActivityComponent::class)
interface UtilsModule {
    @Binds
    fun bindUtils(networkUtils: NetworkUtils): Utils
}