package ru.korobeynikov.p3hiltmodulesscope

import android.app.Application
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideNetworkUtils(app: Application) = NetworkUtils(app)
}