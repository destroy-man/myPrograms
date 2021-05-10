package com.korobeynikov.p0031subcomponentandscope.scopeExample

import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils():NetworkUtils{
        return NetworkUtils()
    }
}