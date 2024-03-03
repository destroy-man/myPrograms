package ru.korobeynikov.network

import dagger.Module
import dagger.Provides

@Module
class NetworkModule {
    @Provides
    fun provideTasksApi() = object : TaskApi {}
}