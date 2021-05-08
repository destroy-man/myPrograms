package com.korobeynikov.p0011introduction

import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideNetworkUtils():NetworkUtils{
        return NetworkUtils()
    }
}