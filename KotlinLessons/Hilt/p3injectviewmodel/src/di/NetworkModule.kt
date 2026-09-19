package ru.korobeynikov.p3injectviewmodel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.korobeynikov.p3injectviewmodel.network.NetworkUtils

@Module
@InstallIn(ViewModelComponent::class)
class NetworkModule {
    @Provides
    fun provideNetworkUtils(): NetworkUtils {
        return NetworkUtils()
    }
}