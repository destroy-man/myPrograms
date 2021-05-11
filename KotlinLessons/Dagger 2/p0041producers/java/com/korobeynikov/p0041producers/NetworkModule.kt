package com.korobeynikov.p0041producers

import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils():NetworkUtils{
        return NetworkUtils()
    }
}